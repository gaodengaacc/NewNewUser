package com.lyun.user.eventbusmessage.login;

import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/8/18
 * do()
 */

public class EventCheckIsBindMessage implements EventMessage<EventCheckIsBindMessage.UnBindMessage> {
   private UnBindMessage message;

    @Override
    public void setMessage(UnBindMessage o) {
        this.message = o;
    }

    @Override
    public UnBindMessage getMessage() {
        return message;
    }

  public static class UnBindMessage{
        public String openId;
        public String loginType;
        public UnBindMessage(String openId,String loginType){
            this.openId = openId;
            this.loginType = loginType;
        }
    }
    public EventCheckIsBindMessage(UnBindMessage message ){
        this.message = message;
    }
}
