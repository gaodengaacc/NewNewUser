package com.lyun.user.api.response;

import com.google.gson.annotations.SerializedName;
import com.lyun.api.response.BaseResponse;

import java.util.List;

public class LawWorldResponse extends BaseResponse {

    /**
     * id : 2c9004d75ac6b7d1015ac6d7e2db0012
     * userId : 13000007009
     * password :
     * email :
     * realName :
     * nickName :
     * realSex :
     * handId :
     * idCard :
     * cardType :
     * cardNo : null
     * empAge :
     * introduction :
     * yunXinToken : 307f0ebe0fdbc9413c359f5262b31f27
     * userType : 1
     * userImg :
     * createTime : null
     * dominList : []
     * new : false
     */

    private String id;
    private String userId;
    private String password;
    private String email;
    private String realName;
    private String nickName;
    private String realSex;
    private String handId;
    private String idCard;
    private String cardType;
    private Object cardNo;
    private String empAge;
    private String introduction;
    private String yunXinToken;
    private String userType;
    private String userImg;
    private Object createTime;
    @SerializedName("new")
    private boolean newX;
    private List<Domin> dominList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealSex() {
        return realSex;
    }

    public void setRealSex(String realSex) {
        this.realSex = realSex;
    }

    public String getHandId() {
        return handId;
    }

    public void setHandId(String handId) {
        this.handId = handId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Object getCardNo() {
        return cardNo;
    }

    public void setCardNo(Object cardNo) {
        this.cardNo = cardNo;
    }

    public String getEmpAge() {
        return empAge;
    }

    public void setEmpAge(String empAge) {
        this.empAge = empAge;
    }

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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserImg() {
        if (userId != null)
            return userImg.trim();
        else return "";
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public boolean isNewX() {
        return newX;
    }

    public void setNewX(boolean newX) {
        this.newX = newX;
    }

    public List<Domin> getDominList() {
        return dominList;
    }

    public void setDominList(List<Domin> dominList) {
        this.dominList = dominList;
    }

    public class Domin {

        /**
         * id : 10
         * code : 112
         * name : 婚姻家庭财产关系
         * description : 婚姻家庭财产关系
         */

        private int id;
        private String code;
        private String name;
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
