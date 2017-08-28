package com.lyun.user.eventbusmessage.homefragment;

import android.content.Intent;

import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/8/23
 * do()
 */

public class EventMainIntentActivityMessage implements EventMessage<Intent> {
    private Intent intent;
    @Override
    public void setMessage(Intent o) {
        this.intent = o;
    }

    @Override
    public Intent getMessage() {
        return intent;
    }
    public EventMainIntentActivityMessage(Intent intent) {
        setMessage(intent);
    }
    public EventMainIntentActivityMessage() {
    }
}
