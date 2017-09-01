package com.lyun.user.eventbusmessage.cardpay;

import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/8/31
 * do()
 */

public class EventPayResultMessage implements EventMessage<String> {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    private boolean isSuccess;
    @Override
    public void setMessage(String o) {
        this.result = o;
    }

    @Override
    public String getMessage() {
        return result;
    }
}
