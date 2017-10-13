package com.lyun.user.fragment;

import android.content.Intent;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.activity.PaySuccessActivity;
import com.lyun.user.activity.ServiceCardDetailActivity;
import com.lyun.user.api.response.ServiceCardListItemResponse;
import com.lyun.user.api.response.ServiceCardResponse;
import com.lyun.user.api.response.WalletChargeAliPayResponse;
import com.lyun.user.api.response.WalletChargeWxPayResponse;
import com.lyun.user.databinding.FragmentServiceCardBinding;
import com.lyun.user.dialog.CardPayDialog;
import com.lyun.user.eventbusmessage.EventListItemMessage;
import com.lyun.user.eventbusmessage.cardpay.EventPayReadyMessage;
import com.lyun.user.eventbusmessage.cardpay.EventPayResultMessage;
import com.lyun.user.eventbusmessage.homefragment.EventMainIntentActivityMessage;
import com.lyun.user.model.WalletChargeModel;
import com.lyun.user.pay.PaySuccessInfo;
import com.lyun.user.pay.alipay.AliPayManager;
import com.lyun.user.pay.wxpay.WXPayManager;
import com.lyun.user.viewmodel.CardPayDialogViewModel;
import com.lyun.user.viewmodel.FragmentServiceCardViewModel;
import com.lyun.user.viewmodel.WalletChargeViewModel;
import com.lyun.user.viewmodel.watchdog.IFragmentServiceCardViewModelCallbacks;
import com.lyun.utils.Screen;
import com.lyun.utils.TipsToast;

import net.funol.databinding.watchdog.Watchdog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServiceCardFragment extends MvvmFragment<FragmentServiceCardBinding, FragmentServiceCardViewModel>
        implements IFragmentServiceCardViewModelCallbacks {

    private CardPayDialog dialog;
    private CardPayDialogViewModel payViewModel;
    private AliPayManager aliPayManager;
    private WXPayManager wxPayManager;
    private String action = "ServiceCardFragment";
    private PaySuccessInfo paySuccessInfo;
    private String actionSign;

    public ServiceCardFragment() {
    }

    public static ServiceCardFragment newInstance() {
        ServiceCardFragment fragment = new ServiceCardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setRetainInstance(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            getFragmentViewModel().onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @NonNull
    @Override
    protected FragmentServiceCardViewModel createViewModel() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getFragmentViewDataBinding().layoutHeightTop.getLayoutParams();
        layoutParams.height = Screen.getStatusBarHeightByReflaction(getContext());
        getFragmentViewDataBinding().layoutHeightTop.setLayoutParams(layoutParams);

        getFragmentViewDataBinding().serviceCardRecycler.setNestedScrollingEnabled(false);

        ViewPager mViewPager = getFragmentViewDataBinding().serviceCardViewpager;

        //mViewPager.setPageMargin(DisplayUtil.dip2px(getContext(), -20));
        mViewPager.setPageTransformer(true, new ServiceCardPageTransformer());
        mViewPager.setOffscreenPageLimit(4);

        // mViewPagerContainer = (RelativeLayout) root.findViewById(R.id.law_world_viewpager_container);
        // 引发bug
        // mViewPagerContainer.setOnTouchListener((v, event) -> mViewPager.dispatchTouchEvent(event));

        getFragmentViewDataBinding().serviceCardRecycler.setOnTouchListener((v, event) -> true);

        FragmentServiceCardViewModel viewModel = new FragmentServiceCardViewModel();
        Watchdog.newBuilder().watch(viewModel).notify(this).build();

        return viewModel;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_service_card;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyStartActivity(EventMainIntentActivityMessage message) {
        //if (message.getMessage().getStringExtra("flag").equals(FragmentServiceCardViewModel.TOP_CLICK_FLAG))
        //    startActivity(new Intent(getContext(), ServiceCardTasteDetailActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemClick(EventListItemMessage message) {
        if (message.getMessage() instanceof ServiceCardListItemResponse) {
            ServiceCardListItemResponse response = (ServiceCardListItemResponse) message.getMessage();
            startActivity(new Intent(getContext(), ServiceCardDetailActivity.class).putExtra("cardId", response.getCardId()));
        }
    }

    @Override
    public void buyCard(ObservableDouble observableField, int fieldId) {
        showPayDialog(observableField.get());
    }

    @Override
    public void navigateCardDetail(ObservableField<ServiceCardResponse> observableField, int fieldId) {
        ServiceCardDetailActivity.start(getActivity(), observableField.get());
    }

    public void showPayDialog(double cost) {
        if (payViewModel == null)
            payViewModel = new CardPayDialogViewModel(cost, getFragmentViewModel().id, action);
        payViewModel.setMoney(cost, getFragmentViewModel().id);
        if (dialog == null)
            dialog = new CardPayDialog(getActivity(), payViewModel);
        dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payReadyClick(EventPayReadyMessage message) {
        actionSign = message.getMessage().action;
        if (!actionSign.equals(action)) return;
        //dialogViewModel.show();
        Observable.just(message.getMessage().type)
                .flatMap(type -> {
                    if (type == WalletChargeViewModel.PayType.ALI)
                        return new WalletChargeModel().getAliWalletChargeOrder(message.getMessage().cardId);
                    else
                        return new WalletChargeModel().getWxWalletChargeOrder(message.getMessage().cardId);
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    dialogViewModel.dismiss();
                    paySuccessInfo = new PaySuccessInfo();
                    if (response instanceof WalletChargeAliPayResponse) {
                        aliPay(((WalletChargeAliPayResponse) response).getSign());
                        paySuccessInfo.imageUrl = ((WalletChargeAliPayResponse) response).getCardImgPath();
                        paySuccessInfo.orderId = ((WalletChargeAliPayResponse) response).getCardOrderNo();
                        paySuccessInfo.tradeTime = ((WalletChargeAliPayResponse) response).getTradeTime();
                        paySuccessInfo.money = ((WalletChargeAliPayResponse) response).getAmount();
                        paySuccessInfo.activeStartTime = ((WalletChargeAliPayResponse) response).getActiveStartTime();
                        paySuccessInfo.activeEndTime = ((WalletChargeAliPayResponse) response).getActiveEndTime();
                    } else {
                        wxPay((WalletChargeWxPayResponse) response);
                        paySuccessInfo.imageUrl = ((WalletChargeWxPayResponse) response).getCardImgPath();
                        paySuccessInfo.orderId = ((WalletChargeWxPayResponse) response).getCardOrderNo();
                        paySuccessInfo.tradeTime = ((WalletChargeWxPayResponse) response).getTradeTime();
                        paySuccessInfo.money = ((WalletChargeWxPayResponse) response).getAmount();
                        paySuccessInfo.activeStartTime = ((WalletChargeWxPayResponse) response).getActiveStartTime();
                        paySuccessInfo.activeEndTime = ((WalletChargeWxPayResponse) response).getActiveEndTime();
                    }
                }, throwable -> {
                    dialogViewModel.dismiss();
                    Toast.makeText(AppApplication.getInstance(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showPayResult(EventPayResultMessage message) {

        if (message.isSuccess()) {
            getFragmentViewModel().paySuccess();
        }

        if (!actionSign.equals(action)) return;

        if (message.isSuccess()) {
            if (dialog != null)
                dialog.dismiss();
            startActivity(new Intent(getActivity(), PaySuccessActivity.class).putExtra("paySuccessInfo", paySuccessInfo));
            return;
        }
        TipsToast tipsToast = TipsToast.makeText(getContext(), message.getMessage(), TipsToast.LENGTH_SHORT);
        tipsToast.setIcon(R.mipmap.icon_pay_failed);
        tipsToast.setText(message.getMessage());
        tipsToast.setTextColor(Color.parseColor("#ff5964"));
        tipsToast.show();
    }

    public class ServiceCardPageTransformer implements ViewPager.PageTransformer {

        private final float MAX_SCALE = 270f / 360;
        private final float MAX_TRANSLATION_X_RATIO = 250f / 330;

        @Override
        public void transformPage(View page, float position) {
            float scale = MAX_SCALE;
            float translationX = 0;
            if (position < -1 || position > 1) {
                // 左划到底 右划到底
            } else if (position <= 0) {
                // 左划
                scale = 1 + position - position * MAX_SCALE;
                translationX = page.getWidth() * (1 - scale) * MAX_TRANSLATION_X_RATIO;
            } else if (position <= 1) {
                // 右划
                scale = 1 - position + position * MAX_SCALE;
                translationX = page.getWidth() * (scale - 1) * MAX_TRANSLATION_X_RATIO;
            }
            // 等比例缩放
            page.setScaleY(scale);
            page.setScaleX(scale);
            page.setTranslationX(translationX);
        }
    }

    public void aliPay(String sign) {
        if (aliPayManager == null)
            aliPayManager = new AliPayManager();
        aliPayManager.alipay(getActivity(), sign);
    }

    public void wxPay(WalletChargeWxPayResponse response) {
        if (wxPayManager == null)
            wxPayManager = new WXPayManager();
        if (!wxPayManager.wxPay(response))
            Toast.makeText(AppApplication.getInstance(), "请安装微信客户端", Toast.LENGTH_LONG).show();
    }
}
