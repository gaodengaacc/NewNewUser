package com.lyun.user.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.R;
import com.lyun.user.model.WalletChargeModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;

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
    public WalletChargeViewModel() {
        init();
    }

    private int PAY_WAY = 2; // 0=绿豆，1=微信，2=支付宝，3=银联，4=总账，5=其他
    @WatchThis
    public final ObservableField<String> aliPay = new ObservableField();
    @WatchThis
    public final ObservableField<String> wxPay = new ObservableField();
    @WatchThis
    public final ObservableBoolean isShowDialog = new ObservableBoolean();
    @WatchThis
    public final ObservableField<String> showText = new ObservableField();//金额为0时


    private void init() {
        availableMin.set("15");
        moneyReduceText.set("15元");
        moneyAddText.set("15元");
        moneyResultText.set("45");
        buyDes.set("1,首次充值-以15分钟为最小购买单元,购买价格为：45元/15分钟;\n2,续费充值-以5分钟为充值单元,购买价格为：15元/5分钟.");
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
                PAY_WAY = 2;
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
        if (PAY_WAY == 2) {
            getAliPayOrder(PAY_WAY + "", Account.preference().getPhone(), moneyResultText.get(), availableMin.get());
//            aliPay.notifyChange();
        } else if (PAY_WAY == 1) {
            wxPay.notifyChange();
        }
    }

    private void doReduceOrAdd(boolean isAdd) {
        if (isAdd) {
            moneyResultText.set((Integer.parseInt(moneyResultText.get()) + 15) + "");
            availableMin.set((Integer.parseInt(availableMin.get()) + 5) + "");
        } else {
            if (Integer.parseInt(moneyResultText.get()) <= 45) {
                showText.set("首次充值不能小于45元");
                return;
            }
            moneyResultText.set((Integer.parseInt(moneyResultText.get()) - 15) + "");
            availableMin.set((Integer.parseInt(availableMin.get()) - 5) + "");
        }
    }

    private void getAliPayOrder(String payType, String handid, String amount, String butTime) {
        new WalletChargeModel().getWalletChargeOrder(payType, handid, amount, butTime)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        apiResult -> {
                            isShowDialog.set(false);
                            if (apiResult.getStatus().equals("0")) {
                                            aliPay.set(apiResult.getContent().getSign());
                            } else {
                                showText.set(apiResult.getDescribe());
                            }
                        }
                );
    }
}
