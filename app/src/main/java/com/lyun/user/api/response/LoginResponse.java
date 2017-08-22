package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * Created by ZHAOWEIWEI on 2017/1/4.
 */

public class LoginResponse extends BaseResponse {

    /**
     * userName : 13838502074
     * telePhone : null
     * identityCardNo : null
     * nickName : null
     * realName : null
     * email : null
     * language : null
     * empAge : null
     * introduction : null
     * yunXinToken : 036db37bd336b5088014f1948f82bf87
     * appToken : abc123
     */

    private String userName;
    private String telePhone;
    private String identityCardNo;
    private String nickName;
    private String realName;
    private String email;
//    private String language;
//    private String empAge;
    private String introduction;
    private String yunXinToken;
    private String appToken;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String cardNo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getIdentityCardNo() {
        return identityCardNo;
    }

    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }
//
//    public String getEmpAge() {
//        return empAge;
//    }
//
//    public void setEmpAge(String empAge) {
//        this.empAge = empAge;
//    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getYunXinToken() {
        return yunXinToken;
    }

    public void setYunXinToken(String yunXinToken) {
        this.yunXinToken = yunXinToken;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }
}
