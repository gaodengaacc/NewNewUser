package com.lyun.user.activity;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.SimpleDialogViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.api.response.WalletChargeWxPayResponse;
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
        viewModel.onBackClick.set(view -> showIsBuy());
        return viewModel;
    }

    @NonNull
    @Override
    protected WalletChargeViewModel createBodyViewModel() {
        return new WalletChargeViewModel().setPropertyChangeListener(this);
    }

    @Override
    public void aliPay(ObservableField<WalletChargeViewModel.AliPayInfo> observableField, int fieldId) {
        if (observableField.get() != null) {
            if (aliPayManager == null)
                aliPayManager = new AliPayManager(observableField.get().getCallBack());
            aliPayManager.alipay(this, observableField.get().getSign());
        }

    }

    @Override
    public void wxPay(ObservableField<WalletChargeWxPayResponse> observableField, int fieldId) {
        if (observableField.get() != null) {
            if (wxPayManager == null)
                wxPayManager = new WXPayManager();
           boolean isWxInstalled= wxPayManager.sendPayReq(this, observableField.get().getAppid(),
                    observableField.get().getPartnerid(),
                    observableField.get().getPrepayid(),
                    observableField.get().getNoncestr(),
                    observableField.get().getTimestamp(),
                    observableField.get().getSign());
            if(!isWxInstalled)
                Toast.makeText(AppApplication.getInstance(),"请安装微信客户端", Toast.LENGTH_LONG).show();
        }

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
    public void showText(ObservableField<String> observableField, int fieldId) {
        Toast.makeText(AppApplication.getInstance(), observableField.get(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void doFinish(BaseObservable observableField, int fieldId) {
        finish();
    }

    private void showIsBuy() {
        SimpleDialogViewModel viewModel = new SimpleDialogViewModel(this);
        viewModel.setInfo("是否确定放弃购买");
        viewModel.setYesBtnText("是");
        viewModel.setCancelBtnText("否");
        viewModel.setOnItemClickListener(new SimpleDialogViewModel.OnItemClickListener() {
            @Override
            public void OnYesClick(View view) {
                finish();
            }

            @Override
            public void OnCancelClick(View view) {

            }
        });
        viewModel.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            showIsBuy();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}