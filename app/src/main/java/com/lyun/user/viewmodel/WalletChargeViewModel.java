package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;

import net.funol.databinding.watchdog.annotations.WatchThis;

/**
 * @author Gordon
 * @since 2017/1/10
 * do()
 */

public class WalletChargeViewModel extends ViewModel {
    public final ObservableField<String> availableMin = new ObservableField<>();
    public final ObservableField<String> moneyResultText = new ObservableField<>();
    public final ObservableField<String> moneyReduceText = new ObservableField<>();
    public final ObservableField<String> moneyAddText = new ObservableField<>();
    public final ObservableField<String> buyDes = new ObservableField<>();
    public final ObservableInt aliSelect = new ObservableInt();
    public final ObservableInt wxSelect = new ObservableInt();
    private Bundle bundleTime = new Bundle();

    public WalletChargeViewModel(Bundle bundleTime) {
        this.bundleTime = bundleTime;

        init();
    }

    private int PAY_WAY = 0; // 0 支付宝 1 微信
    @WatchThis
    public final ObservableField<String> aliPay = new ObservableField();
    @WatchThis
    public final ObservableField<String> wxPay = new ObservableField();
    @WatchThis
    public final ObservableBoolean isShowDialog = new ObservableBoolean();
    @WatchThis
    public final BaseObservable onMoneyResultZero = new BaseObservable();//金额为0时


    private void init() {
        availableMin.set(bundleTime.getString("remainingTime"));
        moneyReduceText.set("15元");
        moneyAddText.set("15元");
        moneyResultText.set("0");
        buyDes.set("1,首次充值-以15分钟为最小购买单元,购买价格为：45元/分钟;\n2,续费充值-以5分钟为充值单元,购买价格为：15元/分钟.");
        aliSelect.set(R.mipmap.wallet_charge_select);
        wxSelect.set(R.mipmap.wallet_charge_unselect);
    }


    public void OnClickView(View view) {
        switch (view.getId()) {
            case R.id.wallet_charge_left:
                doReduceOrAdd(false);
                break;
            case R.id.wallet_charge_right:
                doReduceOrAdd(true);
                break;
            case R.id.wallet_charge_ali:
                PAY_WAY = 0;
                aliSelect.set(R.mipmap.wallet_charge_select);
                wxSelect.set(R.mipmap.wallet_charge_unselect);
                break;
            case R.id.wallet_charge_wx:
                PAY_WAY = 1;
                aliSelect.set(R.mipmap.wallet_charge_unselect);
                wxSelect.set(R.mipmap.wallet_charge_select);
                break;
            case R.id.wallet_charge_yes:
                doSubmit();
                break;
            default:
                break;
        }
    }

    //1,首次充值-以15分钟为最小购买单元,购买价格为：45元/分钟;\n2,续费充值-以5分钟为充值单元,购买价格为：15元/分钟.
    private void doSubmit() {
        if (isShowDialog.get()) {
            isShowDialog.set(false);
        }
        isShowDialog.set(true);
        if (PAY_WAY == 0) {
            aliPay.notifyChange();
        } else {
            wxPay.notifyChange();
        }
    }

    private void doReduceOrAdd(boolean isAdd) {
        if (isAdd) {
            moneyResultText.set((Integer.parseInt(moneyResultText.get()) + 15) + "");
        } else if ("0".equals(moneyResultText.get())) {
            onMoneyResultZero.notifyChange();
        } else {
            moneyResultText.set((Integer.parseInt(moneyResultText.get()) - 15) + "");
        }
    }
}
