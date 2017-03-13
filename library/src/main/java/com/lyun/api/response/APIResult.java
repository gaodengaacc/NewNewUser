package com.lyun.api.response;

import static com.lyun.api.response.APIResult.Status.SUCCESS;

/**
 * @author 赵尉尉
 * @date 2016/12/20
 */

public class APIResult<T> {

    private String status;
    private String describe;
    private T content;

    public APIResult() {
    }

    public APIResult(String status, String describe, T content) {
        this.status = status;
        this.describe = describe;
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public static class Status {
        public static final String ERROR = "-1";
        public static final String SUCCESS = "0";
        public static final String STATUS_TOKEN_EXPIRED = "10";

    }

    public boolean isSuccess() {
        return SUCCESS.equals(status);
    }
}
