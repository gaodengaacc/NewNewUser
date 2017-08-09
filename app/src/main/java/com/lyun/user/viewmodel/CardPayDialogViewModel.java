package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.lyun.library.mvvm.viewmodel.DialogViewModel;
import com.lyun.user.R;
import com.lyun.user.eventbusmessage.cardpay.EventPayReadyMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

/**
 * @author Gordon
 * @since 2017/8/1
 * do()
 */

public class CardPayDialogViewModel extends DialogViewModel {
    public final ObservableInt aliSelect = new ObservableInt();//充值图标
    public final ObservableInt wxSelect = new ObservableInt();
    public final ObservableField<String> buyMoney = new ObservableField<>();
    private WalletChargeViewModel.PayType PAY_WAY = WalletChargeViewModel.PayType.ALI;
    private long lastClickTime;
    private double money;
    private EventPayReadyMessage message;
    private EventPayReadyMessage.PayReadyInfo payReadyInfo;

    public CardPayDialogViewModel(Double money) {
        init(money);
    }

    public void setMoney(Double money) {
        buyMoney.set("￥" + money + "元");
    }

    private void init(Double money) {
        aliSelect.set(R.mipmap.wallet_charge_select);
        wxSelect.set(R.mipmap.wallet_charge_unselect);
        buyMoney.set("￥" + money + "元");
    }

    //点击事件
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.wallet_charge_ali:
                PAY_WAY = WalletChargeViewModel.PayType.ALI;
                aliSelect.set(R.mipmap.wallet_charge_select);
                wxSelect.set(R.mipmap.wallet_charge_unselect);
                break;
            case R.id.wallet_charge_wx:
                PAY_WAY = WalletChargeViewModel.PayType.WX;
                aliSelect.set(R.mipmap.wallet_charge_unselect);
                wxSelect.set(R.mipmap.wallet_charge_select);
                break;
            case R.id.wallet_charge_yes:
                doSubmit(view);
                break;
            default:
                break;
        }
    }

    private void doSubmit(View view) {
        RxView.clicks(view)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(vo -> {
                    payReadyInfo = (payReadyInfo == null) ? (new EventPayReadyMessage.PayReadyInfo()) : payReadyInfo;
                    payReadyInfo.money = money;
                    payReadyInfo.type = PAY_WAY;
                    message = message == null ? new EventPayReadyMessage() : message;
                    message.setMessage(payReadyInfo);
                    EventBus.getDefault().post(message);
                });

    }

    ;

    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
