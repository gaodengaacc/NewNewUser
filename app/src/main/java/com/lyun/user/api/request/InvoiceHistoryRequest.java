package com.lyun.user.api.request;

public class InvoiceHistoryRequest extends BaseRequestBean {

    private int pageid;
    private int pagesize = 20;

    public int getPageid() {
        return pageid;
    }

    public void setPageid(int pageid) {
        this.pageid = pageid;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}