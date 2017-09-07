package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

public class InvoiceHistoryResponse extends BaseResponse {

    private int id;
    private String orderNo;
    private long tradeTime;
    private long filingTime;
    private long activeStartTime;
    private long activeEndTime;
    private ServiceCardResponse card;
    private String userId;
    private double amount;
    private String cardNo;
    private long createTime;
    private String cardState;
    private String invoiceState;
    private String cardType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public long getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(long tradeTime) {
        this.tradeTime = tradeTime;
    }

    public long getFilingTime() {
        return filingTime;
    }

    public void setFilingTime(long filingTime) {
        this.filingTime = filingTime;
    }

    public long getActiveStartTime() {
        return activeStartTime;
    }

    public void setActiveStartTime(long activeStartTime) {
        this.activeStartTime = activeStartTime;
    }

    public long getActiveEndTime() {
        return activeEndTime;
    }

    public void setActiveEndTime(long activeEndTime) {
        this.activeEndTime = activeEndTime;
    }

    public ServiceCardResponse getCard() {
        return card;
    }

    public void setCard(ServiceCardResponse card) {
        this.card = card;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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
