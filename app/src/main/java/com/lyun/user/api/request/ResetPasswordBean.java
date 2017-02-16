package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * @author Gordon
 * @since 2017/2/15
 * do()
 */

public class ResetPasswordBean extends BaseRequest {
    private String userName;
    private String password;
    private String newPassword;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
