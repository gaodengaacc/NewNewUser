package com.lyun.user.api.request;

/**
 * Created by 郑成裕 on 2017/2/15.
 */

public class RegisterVerifyPhoneBean extends BaseRequestBean {
    private String cardNo;

    public RegisterVerifyPhoneBean(String cardNo) {

        this.cardNo = cardNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }


}
