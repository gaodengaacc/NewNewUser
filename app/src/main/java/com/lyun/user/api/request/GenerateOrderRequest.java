package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * Created by ZHAOWEIWEI on 2017/2/16.
 */

public class GenerateOrderRequest extends BaseRequest {

    /**
     * 用户账号
     */
    public String cardNo;
    /**
     * 语种ID
     */
    public String languageId;
    /**
     * 订单类型(0=图文 1=语音)
     */
    public String orderTypeId;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(String orderTypeId) {
        this.orderTypeId = orderTypeId;
    }
}
