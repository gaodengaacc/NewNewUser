package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * @author Gordon
 * @since 2017/2/24
 * do()
 */

public class WalletChargeWxPayResponse extends BaseResponse {
    private String appid;
    private String partnerid;
    private String prepayid;
    private String packaged;
    private String noncestr;
    private String timestamp;
    private String sign;
    private String userOrderid;
    private String tradeTime;//成交日期

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getActiveStartTime() {
        return activeStartTime;
    }

    public void setActiveStartTime(String activeStartTime) {
        this.activeStartTime = activeStartTime;
    }

    public String getActiveEndTime() {
        return activeEndTime;
    }

    public void setActiveEndTime(String activeEndTime) {
        this.activeEndTime = activeEndTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardImgPath() {
        return cardImgPath;
    }

    public void setCardImgPath(String cardImgPath) {
        this.cardImgPath = cardImgPath;
    }

    private String activeStartTime;//有效期开始
    private String activeEndTime;//有效期结束
    private String amount;//购买卡金额
    private String cardImgPath;//服务卡图片地址

    public String getCardOrderNo() {
        return cardOrderNo;
    }

    public void setCardOrderNo(String cardOrderNo) {
        this.cardOrderNo = cardOrderNo;
    }

    private String cardOrderNo;//服务卡订单编号

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUserOrderid() {
        return userOrderid;
    }

    public void setUserOrderid(String userOrderid) {
        this.userOrderid = userOrderid;
    }

    public String getPackaged() {
        return packaged;
    }

    public void setPackaged(String packaged) {
        this.packaged = packaged;
    }
}
