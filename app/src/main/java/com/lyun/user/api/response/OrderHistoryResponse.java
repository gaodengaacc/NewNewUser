package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

public class OrderHistoryResponse extends BaseResponse {

    /**
     * orderNo : 20170900000050
     * name : VIP卡
     * logoImg : card/vip.png
     * tradeTime : 720
     * price : 0.01
     * addressId : null
     * cardState : 1
     * invoiceState : 0
     */

    private String orderNo;
    private String name;
    private String logoImg;
    private String tradeTime;
    private String price;
    private Object addressId;
    /**
     * 服务卡状态
     * -1 不可用
     * 0 未使用
     * 1 使用中
     * 2 已使用完
     * 3 已过期
     * 4 退款中
     * 5 已退款
     */
    private String cardState;
    private String invoiceState;
    private String cardType;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getAddressId() {
        return addressId;
    }

    public void setAddressId(Object addressId) {
        this.addressId = addressId;
    }

    public String getCardState() {
        return cardState;
    }

    public void setCardState(String cardState) {
        this.cardState = cardState;
    }

    public String getInvoiceState() {
        return invoiceState;
    }

    public void setInvoiceState(String invoiceState) {
        this.invoiceState = invoiceState;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
