package com.lyun.api.response;

/**
 * Created by ZHAOWEIWEI on 2017/2/21.
 */

public class Page<T> {

    private int pagecount;
    private T data;

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
