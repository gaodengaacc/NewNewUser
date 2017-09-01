package com.lyun.user.api.request;

/**
 * @author Gordon
 * @since 2017/2/22
 * do()
 */

public class WalletChargeBean extends BaseRequestBean {
    private String payType;
    private String handid = getCardNo();
    private String phone = userId;
    private String cardId;


    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }


    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
