package com.lyun.user.api.request;

/**
 * Created by ZHAOWEIWEI on 2017/2/16.
 */

public class GenerateOrderRequest extends BaseRequestBean {

    /**
     * 语种ID
     */
    public String domainId;
    /**
     * 订单类型(0=图文 1=语音)
     */
    public String orderTypeId;

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(String orderTypeId) {
        this.orderTypeId = orderTypeId;
    }
}
