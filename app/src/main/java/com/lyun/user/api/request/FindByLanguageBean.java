package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 * @author Gordon
 * @since 2017/2/16
 * do()
 */

public class FindByLanguageBean extends BaseRequest{
    private String pageid;
    private String pagesize;

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
