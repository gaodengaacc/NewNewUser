package com.lyun.user.api.request;

/**
 * @author Gordon
 * @since 2017/8/18
 * do()
 */

public class ThirdLoginRegisterBean extends BaseRequestBean {
    private String openId;
    private String loginType;
    private String password;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public ThirdLoginRegisterBean(String userId,String openId,String loginType,String password){
        this.userId = userId;
        this.openId = openId;
        this.loginType = loginType;
        this.password = password;
    }
}
