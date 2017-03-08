package com.lyun.user.im.receiver.attach;

/**
 * Created by ZHAOWEIWEI on 2017/3/7.
 */

public class TranslationOrderFinish {

    /**
     * userOrderId : 17839077660893384704193758
     * orderTypeId : 1
     * orderHand : 15601920157
     */

    private String userOrderId;
    private String orderTypeId;
    private String orderHand;

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public String getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(String orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getOrderHand() {
        return orderHand;
    }

    public void setOrderHand(String orderHand) {
        this.orderHand = orderHand;
    }
}
