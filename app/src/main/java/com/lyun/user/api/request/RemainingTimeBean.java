package com.lyun.user.api.request;

/**
 * Created by 郑成裕 on 2017/2/21.
 */

public class RemainingTimeBean extends BaseRequestBean {
    private String cardNo;


    public RemainingTimeBean(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }


}
