package com.lyun.user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.api.response.ServiceCardResponse;
import com.lyun.user.api.response.WalletChargeAliPayResponse;
import com.lyun.user.api.response.WalletChargeWxPayResponse;
import com.lyun.user.databinding.ActivityServiceCardDetailBinding;
import com.lyun.user.dialog.CardPayDialog;
import com.lyun.user.eventbusmessage.EventActivityFinishMessage;
import com.lyun.user.eventbusmessage.cardpay.EventPayReadyMessage;
import com.lyun.user.eventbusmessage.cardpay.EventPayResultMessage;
import com.lyun.user.eventbusmessage.cardpay.EventShowPayDialogMessage;
import com.lyun.user.model.WalletChargeModel;
import com.lyun.user.pay.PaySuccessInfo;
import com.lyun.user.pay.alipay.AliPayManager;
import com.lyun.user.pay.wxpay.WXPayManager;
import com.lyun.user.viewmodel.CardPayDialogViewModel;
import com.lyun.user.viewmodel.ServiceCardDetailViewModel;
import com.lyun.user.viewmodel.WalletChargeViewModel;
import com.lyun.utils.TipsToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServiceCardDetailActivity extends GeneralToolbarActivity<ActivityServiceCardDetailBinding, ServiceCardDetailViewModel> {

    public static final String EXTRA_CARD = "extra_card";
    private PaySuccessInfo paySuccessInfo;
    private CardPayDialog dialog;
    private CardPayDialogViewModel payViewModel;
    private AliPayManager aliPayManager;
    private WXPayManager wxPayManager;
    private String action = "ServiceCardDetailActivity";
    private String actionSign;
    public static void start(Context context, ServiceCardResponse card) {
        Intent intent = new Intent(context, ServiceCardDetailActivity.class);
        intent.putExtra(EXTRA_CARD, card);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_service_card_detail;
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel = super.createTitleViewModel();
        toolbarViewModel.title.set("服务内容详情页");
        toolbarViewModel.onBackClick.set(view -> finish());
        return toolbarViewModel;
    }

    @NonNull
    @Override
    protected ServiceCardDetailViewModel createBodyViewModel() {
        return new ServiceCardDetailViewModel((ServiceCardResponse) getIntent().getSerializableExtra(EXTRA_CARD));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinishActivity(EventActivityFinishMessage message) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payReadyClick(EventPayReadyMessage message) {
        actionSign = message.getMessage().action;
        if (!actionSign.equals(action)) return;
        dialogViewModel.show();
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
        if (!actionSign.equals(action)) return;
        if (message.isSuccess()) {
            if (dialog != null) dialog.dismiss();
            startActivity(new Intent(this, PaySuccessActivity.class).putExtra("paySuccessInfo", paySuccessInfo));
            return;
        }
        TipsToast tipsToast = TipsToast.makeText(this, message.getMessage(), TipsToast.LENGTH_SHORT);
        tipsToast.setIcon(R.mipmap.icon_pay_failed);
        tipsToast.setText(message.getMessage());
        tipsToast.setTextColor(Color.parseColor("#ff5964"));
        tipsToast.show();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showPayResult(EventShowPayDialogMessage message) {
        showPayDialog(message.getMessage());
    }

    public void aliPay(String sign) {
        if (aliPayManager == null)
            aliPayManager = new AliPayManager();
        aliPayManager.alipay(this, sign);
    }

    public void wxPay(WalletChargeWxPayResponse response) {
        if (wxPayManager == null)
            wxPayManager = new WXPayManager();
        if (!wxPayManager.wxPay(response))
            Toast.makeText(AppApplication.getInstance(), "请安装微信客户端", Toast.LENGTH_LONG).show();
    }

    public void showPayDialog(double cost) {
        if (payViewModel == null)
            payViewModel = new CardPayDialogViewModel(cost, getBodyViewModel().cardId, action);
        payViewModel.setMoney(cost, getBodyViewModel().cardId);
        if (dialog == null)
            dialog = new CardPayDialog(this, payViewModel);
        dialog.show();
    }
}
