package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;
import com.lyun.user.Account;

/**
 * Created by ZHAOWEIWEI on 2017/3/15.
 */

public class BaseRequestBean extends BaseRequest {

    /**
     * 用户类型(0客户 1翻译)
     */
    private String userType = "0";

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    private String cardNo = Account.preference().getCardNo();
}
