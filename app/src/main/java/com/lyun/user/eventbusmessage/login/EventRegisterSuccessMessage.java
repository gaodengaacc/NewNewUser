package com.lyun.user.eventbusmessage.login;

import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/9/6
 * do()
 */

public class EventRegisterSuccessMessage implements EventMessage<String> {
    private String username;

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    private String passward;

    @Override
    public void setMessage(String o) {
        this.username = o;
    }

    @Override
    public String getMessage() {
        return username;
    }

    public EventRegisterSuccessMessage(String username, String passward) {
        this.username = username;
        this.passward = passward;
    }
}
