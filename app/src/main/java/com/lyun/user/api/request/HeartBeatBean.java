package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * Created by ZHAOWEIWEI on 2017/3/1.
 */

public class HeartBeatBean extends BaseRequest {

    /**
     * userOrderId :订单号
     * cardNo :手机号
     */

    private String userOrderId;
    private String cardNo;
    /**
     * 用户类型(0客户 1翻译)
     */
    private String userType;

    public HeartBeatBean(String userOrderId, String cardNo, String userType) {
        this.userOrderId = userOrderId;
        this.cardNo = cardNo;
        this.userType = userType;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
