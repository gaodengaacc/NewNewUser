package com.lyun.user.eventbusmessage;

/**
 * @author Gordon
 * @since 2017/7/31
 * do()
 */

public class EventListItemMessage implements EventMessage {
   private Object object;

    @Override
    public void setMessage(Object o) {
        this.object = o;
    }

    @Override
    public Object getMessage() {
        return object;
    }

    public EventListItemMessage(Object o) {
        this.object = o;
    }
}
