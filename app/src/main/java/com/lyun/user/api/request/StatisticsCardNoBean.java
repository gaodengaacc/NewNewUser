package com.lyun.user.api.request;

/**
 * Created by 郑成裕 on 2017/2/23.
 */

public class StatisticsCardNoBean extends BaseRequestBean {
    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public StatisticsCardNoBean(String cardNo) {

        this.cardNo = cardNo;
    }
}
