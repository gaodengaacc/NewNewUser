package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * Created by ZHAOWEIWEI on 2017/3/1.
 */

public class CancelTranslationOrderBean extends BaseRequest {

    private String userOrderId;

    public CancelTranslationOrderBean(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }
}
