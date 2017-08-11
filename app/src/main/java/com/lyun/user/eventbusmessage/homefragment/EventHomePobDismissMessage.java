package com.lyun.user.eventbusmessage.homefragment;

import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/7/27
 * do()
 */

public class EventHomePobDismissMessage implements EventMessage<Boolean> {
    private boolean isDismiss;

    @Override
    public void setMessage(Boolean o) {
          isDismiss = o;
    }

    @Override
    public Boolean getMessage() {
        return isDismiss;
    }
    public EventHomePobDismissMessage(boolean isDismiss){
        this.isDismiss = isDismiss;
    }

}
