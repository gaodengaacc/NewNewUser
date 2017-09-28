package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

public class InvoiceHistoryResponse extends BaseResponse {

    /**
     * orderNo : 20170900000196
     * name : 普通卡
     * logoImg : card/ordinary.png
     * tradeTime : 2017-09-26 15:49;06
     * price : 0.01
     * addressId : 128
     * company : null
     * registrationNumber : null
     * invoiceRise : null
     * cardState : 1
     * invoiceState : 2
     * cardType : 普通卡
     */

    private String orderNo;
    private String name;
    private String logoImg;
    private String tradeTime;
    private String filingTime;
    private String price;
    private String addressId;
    private String company;
    private String registrationNumber;
    private String invoiceRise;
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

    public String getFilingTime() {
        return filingTime;
    }

    public void setFilingTime(String filingTime) {
        this.filingTime = filingTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getInvoiceRise() {
        return invoiceRise;
    }

    public void setInvoiceRise(String invoiceRise) {
        this.invoiceRise = invoiceRise;
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
