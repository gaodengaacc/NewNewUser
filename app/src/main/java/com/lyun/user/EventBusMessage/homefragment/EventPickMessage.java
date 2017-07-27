package com.lyun.user.EventBusMessage.homefragment;

import com.lyun.user.EventBusMessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/7/27
 * do()
 */

public class EventPickMessage implements EventMessage<Boolean> {
    private boolean isShow;
    @Override
    public void setMessage(Boolean o) {
        isShow = o;
    }

    @Override
    public Boolean getMessage() {
        return isShow;
    }
}
