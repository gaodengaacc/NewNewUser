package com.lyun.user.api.request;

/**
 * @author Gordon
 * @since 2017/3/4
 * do()
 */

public class TranslatorStatusBean extends BaseRequestBean {

    // 用户类型(0客户 1翻译)
    private String userOrderid;
    private String phoneState;//通话标记 0 =开始通话， 1= 会话结束

    public TranslatorStatusBean(String userOrderid, String phoneState) {
        this.userOrderid = userOrderid;
        this.phoneState = phoneState;
    }

    public String getUserOrderid() {
        return userOrderid;
    }

    public void setUserOrderid(String userOrderid) {
        this.userOrderid = userOrderid;
    }

    public String getPhoneState() {
        return phoneState;
    }

    public void setPhoneState(String phoneState) {
        this.phoneState = phoneState;
    }
}
