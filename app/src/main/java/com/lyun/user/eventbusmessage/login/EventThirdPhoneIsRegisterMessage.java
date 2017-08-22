package com.lyun.user.eventbusmessage.login;

import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/8/18
 * do()
 */

public class EventThirdPhoneIsRegisterMessage implements EventMessage<Boolean> {
    private Boolean message;
    @Override
    public void setMessage(Boolean o) {
        this.message = o;
    }

    @Override
    public Boolean getMessage() {
        return message;
    }
    public EventThirdPhoneIsRegisterMessage(Boolean message){
        this.message = message;
    }
}
