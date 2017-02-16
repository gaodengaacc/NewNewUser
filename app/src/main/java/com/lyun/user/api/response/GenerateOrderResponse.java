package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * Created by ZHAOWEIWEI on 2017/2/16.
 */

public class GenerateOrderResponse extends BaseResponse {

    public String userorderid;

    public String getUserorderid() {
        return userorderid;
    }

    public void setUserorderid(String userorderid) {
        this.userorderid = userorderid;
    }
}
