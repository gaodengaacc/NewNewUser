package com.lyun.user.EventBusMessage.homefragment;

import com.lyun.user.EventBusMessage.EventMessage;

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

}
