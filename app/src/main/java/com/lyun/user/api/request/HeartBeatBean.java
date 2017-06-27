package com.lyun.user.api.request;

/**
 * Created by ZHAOWEIWEI on 2017/3/1.
 */

public class HeartBeatBean extends BaseRequestBean {

    /**
     * userOrderId :订单号
     * cardNo :手机号
     */

    private String userOrderId;
    private String cardNo;

    public HeartBeatBean(String userOrderId, String cardNo) {
        this.userOrderId = userOrderId;
        this.cardNo = cardNo;
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

}
