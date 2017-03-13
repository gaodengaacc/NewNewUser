package com.lyun.api.exception;

import com.lyun.api.response.APIResult;

/**
 * Created by ZHAOWEIWEI on 2017/2/23.
 */

public class APINotSuccessException extends APIException {

    private String status = "";

    public APINotSuccessException(APIResult result) {
        super(result.getDescribe());
        this.status = result.getStatus();
    }

    public String getStatus() {
        return status;
    }
}
