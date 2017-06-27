package com.lyun.http;

/**
 * Created by ZHAOWEIWEI on 2016/12/16.
 */

public class APIResult<T> {

    private T content;
    private int status;
    private String describe;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }


}
