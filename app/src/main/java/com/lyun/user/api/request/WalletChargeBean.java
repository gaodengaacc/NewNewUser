package com.lyun.user.api.request;

/**
 * @author Gordon
 * @since 2017/2/22
 * do()
 */

public class WalletChargeBean extends BaseRequestBean {
    private String payType;
    private String handid;
    private String amount;
    private String buytime;
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getHandid() {
        return handid;
    }

    public void setHandid(String handid) {
        this.handid = handid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBuyTime() {
        return buytime;
    }

    public void setBuyTime(String buytime) {
        this.buytime = buytime;
    }


}
