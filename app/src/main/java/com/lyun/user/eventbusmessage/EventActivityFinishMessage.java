package com.lyun.user.eventbusmessage;

/**
 * @author Gordon
 * @since 2017/8/1
 * do()
 */

public class EventActivityFinishMessage implements EventMessage<Boolean> {
    private boolean o;

    @Override
    public void setMessage(Boolean o) {
        this.o = o;
    }

    @Override
    public Boolean getMessage() {
        return o;
    }

    public EventActivityFinishMessage(boolean o) {
        this.o = o;
    }
}
