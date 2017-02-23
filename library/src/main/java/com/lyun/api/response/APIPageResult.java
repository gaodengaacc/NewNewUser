package com.lyun.api.response;

/**
 * @author Gordon
 * @since 2017/2/23
 * do()
 */

public class APIPageResult<T> {
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
