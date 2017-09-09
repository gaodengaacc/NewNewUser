package com.lyun.user.eventbusmessage.login;

import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/9/9
 * do()
 */

public class EventWxLoginFailedMessage implements EventMessage<String> {
    private String message;

    @Override
    public void setMessage(String o) {
        this.message = o;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public EventWxLoginFailedMessage(String message) {
        this.message = message;
    }
}
