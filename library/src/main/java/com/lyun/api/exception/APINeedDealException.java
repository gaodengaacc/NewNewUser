package com.lyun.api.exception;

import com.lyun.api.response.APIResult;

/**
 * @author Gordon
 * @since 2017/8/29
 * do()
 */

public class APINeedDealException extends APINotSuccessException {
    public APINeedDealException(APIResult result) {
        super(result);
    }
}
