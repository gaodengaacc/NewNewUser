package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * @author Gordon
 * @since 2017/3/4
 * do()
 */

public class TranslatorStatusResponse extends BaseResponse {
    private String userOrderid;
    private String orderStateid;//完成状态标记 :0=未完成,1=通话开始,2=通话中,3=已完成（有通话完成标记完成）,4=已完成（无通话完成标记完成）,5=已结束（无通话开始标和结束标记结束）,6=已结束（有通话开始标和无一次结束标记结束）,7=已结束（超时结束)
    private String orderState;

    public String getUserOrderid() {
        return userOrderid;
    }

    public void setUserOrderid(String userOrderid) {
        this.userOrderid = userOrderid;
    }

    public String getOrderStateid() {
        return orderStateid;
    }

    public void setOrderStateid(String orderStateid) {
        this.orderStateid = orderStateid;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
