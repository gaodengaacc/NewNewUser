package com.lyun.user.api.request;

/**
 * @author Gordon
 * @since 2017/8/18
 * do()
 */

public class ThirdLoginBindBean extends BaseRequestBean {
    private String openId;
    private String loginType;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public ThirdLoginBindBean(String userId,String openId,String loginType){
        this.userId = userId;
        this.openId = openId;
        this.loginType = loginType;
    }
}
