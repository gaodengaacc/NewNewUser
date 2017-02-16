package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * Created by 郑成裕 on 2017/2/15.
 */

public class RegisterVerifyPhoneBean extends BaseRequest {
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
