package com.lyun.user.api.request;

public class ApplyForInvoiceRequest extends BaseRequestBean {

    private String orderNo;//订单编号
    private String addressId;//收货地址ID
    private String company;//开票公司
    private String registrationNumber;//纳税人识别号
    private String invoiceRise;//发票抬头 0个人 1单位

    public ApplyForInvoiceRequest(String orderNo, String addressId, String company, String registrationNumber, String invoiceRise) {
        this.orderNo = orderNo;
        this.addressId = addressId;
        this.company = company;
        this.registrationNumber = registrationNumber;
        this.invoiceRise = invoiceRise;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
}
