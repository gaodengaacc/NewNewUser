package com.lyun.user.eventbusmessage.cardpay;

import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/8/1
 * do()
 */

public class EventShowPayDialogMessage implements EventMessage<Double> {
    private Double money;
    @Override
    public void setMessage(Double o) {
        this.money = o;
    }

    @Override
    public Double getMessage() {
        return money;
    }
    public EventShowPayDialogMessage(Double money){
        this.money = money;
    }
}
