package com.lyun.user.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.user.Account;
import com.lyun.user.R;
import com.lyun.user.api.response.WalletChargeAliPayResponse;
import com.lyun.user.api.response.WalletChargeWxPayResponse;
import com.lyun.user.databinding.ActivityServiceCardDetailBinding;
import com.lyun.user.dialog.CardPayDialog;
import com.lyun.user.eventbusmessage.EventActivityFinishMessage;
import com.lyun.user.eventbusmessage.cardpay.EventPayReadyMessage;
import com.lyun.user.eventbusmessage.cardpay.EventShowPayDialogMessage;
import com.lyun.user.model.WalletChargeModel;
import com.lyun.user.viewmodel.CardPayDialogViewModel;
import com.lyun.user.viewmodel.ServiceCardDetailViewModel;
import com.lyun.user.viewmodel.WalletChargeViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServiceCardDetailActivity extends MvvmActivity<ActivityServiceCardDetailBinding, ServiceCardDetailViewModel> {
    private CardPayDialog dialog;
    private CardPayDialogViewModel payViewModel;


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

    @NonNull
    @Override
    protected ServiceCardDetailViewModel createViewModel() {
        return new ServiceCardDetailViewModel(getIntent().getIntExtra("cardId", 0));
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_service_card_detail;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFinishActivity(EventActivityFinishMessage message) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showPayDialog(EventShowPayDialogMessage message) {
        if (payViewModel == null)
            payViewModel = new CardPayDialogViewModel(message.getMessage());
        payViewModel.setMoney(message.getMessage());
        if (dialog == null)
            dialog = new CardPayDialog(this, payViewModel);
        dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payReadClick(EventPayReadyMessage message) {
        dialogViewModel.show();
        new WalletChargeModel().getWalletChargeOrder(message.getMessage().type.value, Account.preference().getPhone(), String.valueOf(message.getMessage().money), "5")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        apiResult -> {
                            dialogViewModel.dismiss();
                            APIResult result = (APIResult) apiResult;
                            if (result.isSuccess()) {
                                if (message.getMessage().type == WalletChargeViewModel.PayType.ALI) {//支付宝
                                    WalletChargeAliPayResponse response = (WalletChargeAliPayResponse) result.getContent();
                                } else if (message.getMessage().type == WalletChargeViewModel.PayType.WX) {//微信
                                    WalletChargeWxPayResponse response = (WalletChargeWxPayResponse) result.getContent();
                                    Account.preference().saveWxAppId(response.getAppid());
                                }
                            } else {
                                Toast.makeText(getBaseContext(),result.getDescribe(),Toast.LENGTH_LONG).show();
                            }

                        }, throwable -> {
                        }
                );
    }
}
