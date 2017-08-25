package com.lyun.user.eventbusmessage.address;

import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/8/23
 * do()
 */

public class EventCityPickDialogShowMessage implements EventMessage<Boolean> {
    private boolean isShow;
    @Override
    public void setMessage(Boolean o) {
        this.isShow = o;
    }

    @Override
    public Boolean getMessage() {
        return isShow;
    }
    public EventCityPickDialogShowMessage(Boolean isShow){
        this.isShow = isShow;
    }
}
