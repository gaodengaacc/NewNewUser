package com.lyun.user.eventbusmessage;

import android.content.Intent;

/**
 * @author Gordon
 * @since 2017/7/28
 * do()
 */

public class EventIntentActivityMessage implements EventMessage<Intent> {
    private Intent intent;
    @Override
    public void setMessage(Intent o) {
        this.intent = o;
    }

    @Override
    public Intent getMessage() {
        return intent;
    }
}
