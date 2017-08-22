package com.lyun.user.eventbusmessage.mainactivity;

import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/8/14
 * do()
 */

public class EventFinishAllActivityMessage implements EventMessage<Boolean> {
    private Boolean o;

    @Override
    public void setMessage(Boolean o) {
        this.o = o;
    }

    @Override
    public Boolean getMessage() {
        return o;
    }

    public EventFinishAllActivityMessage(Boolean o) {
        this.o = o;
    }
}
