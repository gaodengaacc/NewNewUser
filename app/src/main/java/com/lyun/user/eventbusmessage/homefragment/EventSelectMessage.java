package com.lyun.user.eventbusmessage.homefragment;

import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/7/27
 * do()
 */

public class EventSelectMessage implements EventMessage<FindLanguageResponse> {
    private  FindLanguageResponse response;
    @Override
    public void setMessage(FindLanguageResponse o) {
        response = o;
    }

    @Override
    public FindLanguageResponse getMessage() {
        return response;
    }
    public EventSelectMessage(FindLanguageResponse response){
        this.response = response;
    }
}
