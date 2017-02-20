package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * Created by 郑成裕 on 2017/2/20.
 */

public class CheckVerificationBean extends BaseRequest {
    private String name;
    private String verification;

    public CheckVerificationBean(String name, String verification) {
        this.name = name;
        this.verification = verification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }
}
