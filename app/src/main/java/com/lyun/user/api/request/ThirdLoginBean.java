package com.lyun.user.api.request;

/**
 * @author Gordon
 * @since 2017/8/16
 * do()
 */

public class ThirdLoginBean extends BaseRequestBean {
    private String openId;

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    private String loginType;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public ThirdLoginBean(String openId, String loginType) {
        this.openId = openId;
        this.loginType = loginType;
    }
}
