package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * @author Gordon
 * @since 2017/8/31
 * do()
 */

public class AccountBindResponse extends BaseResponse {
    private String openId;
    private String userId;
    private int loginType = -1; // 0:qq 1:wx 2:wb
    public static final int QQ_CHANNEL = 0;
    public static final int WX_CHANNEL = 1;
    public static final int WB_CHANNEL = 2;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

}
