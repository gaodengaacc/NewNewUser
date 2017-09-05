package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * Created by ZHAOWEIWEI on 2017/9/4.
 */

public class LawWorldRequest extends BaseRequest {

    private int pageid;
    private int pagesize = 20;

    public LawWorldRequest(int page) {
        this.pageid = page;
    }

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
