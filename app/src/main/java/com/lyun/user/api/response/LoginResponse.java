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
     * appKey : abc123
     */

    private String userName;
    private Object telePhone;
    private Object identityCardNo;
    private Object nickName;
    private Object realName;
    private Object email;
    private Object language;
    private Object empAge;
    private Object introduction;
    private String yunXinToken;
    private String appKey;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(Object telePhone) {
        this.telePhone = telePhone;
    }

    public Object getIdentityCardNo() {
        return identityCardNo;
    }

    public void setIdentityCardNo(Object identityCardNo) {
        this.identityCardNo = identityCardNo;
    }

    public Object getNickName() {
        return nickName;
    }

    public void setNickName(Object nickName) {
        this.nickName = nickName;
    }

    public Object getRealName() {
        return realName;
    }

    public void setRealName(Object realName) {
        this.realName = realName;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getLanguage() {
        return language;
    }

    public void setLanguage(Object language) {
        this.language = language;
    }

    public Object getEmpAge() {
        return empAge;
    }

    public void setEmpAge(Object empAge) {
        this.empAge = empAge;
    }

    public Object getIntroduction() {
        return introduction;
    }

    public void setIntroduction(Object introduction) {
        this.introduction = introduction;
    }

    public String getYunXinToken() {
        return yunXinToken;
    }

    public void setYunXinToken(String yunXinToken) {
        this.yunXinToken = yunXinToken;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
