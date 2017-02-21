package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * Created by 郑成裕 on 2017/2/21.
 */

public class FindPasswordBean extends BaseRequest {
    private String userName;
    private String verification;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public FindPasswordBean(String userName, String verification, String password) {
        this.userName = userName;
        this.verification = verification;
        this.password = password;
    }


}
