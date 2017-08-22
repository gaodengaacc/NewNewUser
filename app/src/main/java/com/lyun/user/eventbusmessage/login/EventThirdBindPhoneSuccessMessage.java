package com.lyun.user.eventbusmessage.login;

import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/8/18
 * do()
 */

public class EventThirdBindPhoneSuccessMessage implements EventMessage<EventThirdBindPhoneSuccessMessage.BindMessage> {
    private BindMessage message;

    @Override
    public void setMessage(BindMessage o) {
        this.message = o;
    }

    @Override
    public BindMessage getMessage() {
        return message;
    }

    public static class BindMessage {
        public String openId;
        public String loginType;

        public BindMessage(String openId, String loginType) {
            this.openId = openId;
            this.loginType = loginType;
        }
    }

    public EventThirdBindPhoneSuccessMessage(BindMessage message) {
        this.message = message;
    }
}
