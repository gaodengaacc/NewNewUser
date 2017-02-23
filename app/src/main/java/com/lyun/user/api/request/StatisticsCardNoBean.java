package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * Created by 郑成裕 on 2017/2/23.
 */

public class StatisticsCardNoBean extends BaseRequest {
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
