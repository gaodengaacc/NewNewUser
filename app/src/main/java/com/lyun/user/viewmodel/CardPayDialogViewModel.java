package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.lyun.library.mvvm.command.RelayCommand;
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
    public String id;
    private String action;

    public CardPayDialogViewModel(Double money, String id, String action) {
        this.id = id;
        this.action = action;
        init(money);
    }

    public void setMoney(Double money, String id) {
        this.money = money;
        this.id = id;
        buyMoney.set("￥" + money + "元");
    }

    private void init(Double money) {
        this.money = money;
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
            default:
                break;
        }
    }

    public RelayCommand<View> clickBind = new RelayCommand<View>((view) -> {
        RxView.clicks(view)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(vo -> {
                    payReadyInfo = (payReadyInfo == null) ? (new EventPayReadyMessage.PayReadyInfo()) : payReadyInfo;
                    payReadyInfo.money = money;
                    payReadyInfo.type = PAY_WAY;
                    payReadyInfo.cardId = id;
                    payReadyInfo.action = action;
                    message = message == null ? new EventPayReadyMessage() : message;
                    message.setMessage(payReadyInfo);
                    EventBus.getDefault().post(message);
                });
    });

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
