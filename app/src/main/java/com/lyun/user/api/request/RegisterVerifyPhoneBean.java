package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * Created by 郑成裕 on 2017/2/15.
 */

public class RegisterVerifyPhoneBean extends BaseRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegisterVerifyPhoneBean(String name) {
        this.name = name;
    }
}
