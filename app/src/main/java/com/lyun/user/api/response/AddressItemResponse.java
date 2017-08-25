package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * @author Gordon
 * @since 2017/8/22
 * do()
 */

public class AddressItemResponse extends BaseResponse {
    private String userName;
    private String phoneNum;
    private String address;

    public boolean isDefaultAddress() {
        return isDefaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        isDefaultAddress = defaultAddress;
    }

    private boolean isDefaultAddress;

    public AddressItemResponse(String userName, String phoneNum, String address,boolean isDefaultAddress) {
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.isDefaultAddress = isDefaultAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
