package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * Created by 郑成裕 on 2017/2/15.
 */

public class RegisterVerifyPhoneRespose extends BaseResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
