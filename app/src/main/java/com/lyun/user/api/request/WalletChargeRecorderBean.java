package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * @author Gordon
 * @since 2017/2/23
 * do()
 */

public class WalletChargeRecorderBean extends BaseRequest {
    private String cardNo;
    private String pageid;
    private String pagesize;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }
}
