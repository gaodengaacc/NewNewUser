package com.lyun.user.activity;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityWalletChargeBinding;
import com.lyun.user.pay.alipay.AliPayManager;
import com.lyun.user.pay.wxpay.WXPayManager;
import com.lyun.user.viewmodel.WalletChargeViewModel;
import com.lyun.user.viewmodel.watchdog.IWalletChargeViewModelCallbacks;

/**
 * @author Gordon
 * @since 2017/1/10
 * do()
 */

public class WalletChargeActivity extends GeneralToolbarActivity<ActivityWalletChargeBinding, WalletChargeViewModel> implements IWalletChargeViewModelCallbacks {
    private AliPayManager aliPayManager;
    private WXPayManager wxPayManager;
    private Intent intent = new Intent();
    private Bundle bundle = new Bundle();

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_wallet_charge;
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("购买");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @NonNull
    @Override
    protected WalletChargeViewModel createBodyViewModel() {
        intent = getIntent();
        bundle = intent.getExtras();
        return new WalletChargeViewModel(bundle).setPropertyChangeListener(this);
    }

    @Override
    public void aliPay(ObservableField<String> observableField, int fieldId) {
        Toast.makeText(getApplicationContext(), "AliPAy!", Toast.LENGTH_LONG).show();
//        aliPayManager = new AliPayManager(new OnPayCallBack() {
//            @Override
//            public void onSuccess() {
//                dialogViewModel.dismiss();
//                Toast.makeText(getApplicationContext(),"支付成功！！",Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(String des) {
//                dialogViewModel.dismiss();
//                Toast.makeText(getApplicationContext(),des,Toast.LENGTH_LONG).show();
//            }
//        });
//        aliPayManager.alipay(this,"");
    }

    @Override
    public void wxPay(ObservableField<String> observableField, int fieldId) {
//        new WXPayManager(this).sendPayReq();
        Toast.makeText(getApplicationContext(), "WxPAy!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void isShowDialog(ObservableBoolean observableField, int fieldId) {
        if (observableField.get()) {
            dialogViewModel.show();
        } else {
            dialogViewModel.dismiss();
        }
    }

    @Override
    public void onMoneyResultZero(BaseObservable observableField, int fieldId) {
        Toast.makeText(this, "金额不得小于0元！", Toast.LENGTH_LONG).show();
    }
}