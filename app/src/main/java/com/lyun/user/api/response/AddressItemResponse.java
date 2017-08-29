package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * @author Gordon
 * @since 2017/8/22
 * do()
 */

public class AddressItemResponse extends BaseResponse {
    private String id;
    private String recipients;
    private String province = "";
    private String city = "";
    private String district = "";
    private String street = "";
    private String detailAddress;
    private String phoneNum;
    private String isDefault = "0"; //是否为默认地址 0否 1是

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }


    public AddressItemResponse(String id, String recipients, String phoneNum, String province, String city, String district, String street, String detailAddress, String isDefault) {
        this.id = id;
        this.recipients = recipients;
        this.phoneNum = phoneNum;
        this.province = province;
        this.city = city;
        this.district = district;
        this.street = street;
        this.detailAddress = detailAddress;
        this.isDefault = isDefault;
    }

    public AddressItemResponse() {
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
