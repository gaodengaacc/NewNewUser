package com.lyun.user.eventbusmessage.cardpay;

import com.lyun.user.eventbusmessage.EventMessage;
import com.lyun.user.viewmodel.WalletChargeViewModel;

/**
 * @author Gordon
 * @since 2017/8/1
 * do()
 */

public class EventPayReadyMessage implements EventMessage<EventPayReadyMessage.PayReadyInfo> {
    private PayReadyInfo info;
    @Override
    public void setMessage(PayReadyInfo o) {
        info = o;
    }

    @Override
    public PayReadyInfo getMessage() {
        return info;
    }

    public EventPayReadyMessage(PayReadyInfo info) {
        this.info = info;
    }

    public EventPayReadyMessage() {

    }

    public static class PayReadyInfo {
        public double money;
        public WalletChargeViewModel.PayType type;
        public String cardId;
        public String action;

        public PayReadyInfo(double money, WalletChargeViewModel.PayType type, String cardId,String action) {
            this.money = money;
            this.type = type;
            this.cardId = cardId;
            this.action = action;
        }

        public PayReadyInfo() {

        }
    }
}
