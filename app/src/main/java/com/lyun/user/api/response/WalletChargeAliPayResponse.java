package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * @author Gordon
 * @since 2017/2/23
 * do()
 */

public class WalletChargeAliPayResponse extends BaseResponse {
    private String sign;
    private String signType;
    private String userOrderid;
    private String tradeTime;//成交日期
    private String activeStartTime;//有效期开始
    private String activeEndTime;//有效期结束
    private String amount;//购买卡金额

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

    private String cardImgPath;//服务卡图片地址

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getUserOrderid() {
        return userOrderid;
    }

    public void setUserOrderid(String userOrderid) {
        this.userOrderid = userOrderid;
    }
}
