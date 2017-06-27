package com.lyun.user.api.request;

/**
 * Created by 郑成裕 on 2017/2/15.
 */

public class RegisterVerifyPhoneBean extends BaseRequestBean {
    private String cardNo;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RegisterVerifyPhoneBean(String cardNo, String status) {
        this.cardNo = cardNo;
        this.status = status;

    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }


}
