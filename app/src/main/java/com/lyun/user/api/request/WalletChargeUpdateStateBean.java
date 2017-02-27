package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * @author Gordon
 * @since 2017/2/24
 * do()
 */

public class WalletChargeUpdateStateBean extends BaseRequest{
    private String userOrderid;
    private String opstateId;

    public String getUserOrderid() {
        return userOrderid;
    }

    public void setUserOrderid(String userOrderid) {
        this.userOrderid = userOrderid;
    }

    public String getOpstateId() {
        return opstateId;
    }

    public void setOpstateId(String opstateId) {
        this.opstateId = opstateId;
    }
}
