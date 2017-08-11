package com.lyun.user.eventbusmessage;

/**
 * @author Gordon
 * @since 2017/7/27
 * do()
 */

public class EventToastMessage implements EventMessage<String> {
    private String message;

    @Override
    public void setMessage(String o) {
        message =o;
    }

    @Override
    public String getMessage() {
        return message;
    }
    public EventToastMessage(String message){
        this.message = message;
    }
    public EventToastMessage(){
    }
}
