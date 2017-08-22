package com.lyun.user.api.request;

/**
 * @author Gordon
 * @since 2017/8/17
 * do()
 */

public class WxGetOpenIdBean {
    private String appid;
    private String secret;
    private String code;
    private String grant_type = "authorization_code";

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public WxGetOpenIdBean() {

    }

    public WxGetOpenIdBean(String appid, String secret, String code) {
        this.appid = appid;
        this.secret = secret;
        this.code = code;
    }
}
