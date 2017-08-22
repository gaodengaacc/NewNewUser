package com.lyun.user.eventbusmessage.login;

import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/8/16
 * do()
 */

public class EventWbLoginMessage implements EventMessage<String> {
    private String o;
    @Override
    public void setMessage(String o) {
        this.o = o;
    }

    @Override
    public String getMessage() {
        return o;
    }
}
