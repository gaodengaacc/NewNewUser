package com.lyun.api.request;

import java.io.Serializable;

/**
 * 网络请求实体的基类
 *
 * @author 赵尉尉
 * @date 2016/12/20
 */
public class BaseRequest implements Serializable {

    private String appTypeId = "0";

    public String getAppTypeId() {
        return appTypeId;
    }

    public void setAppTypeId(String appTypeId) {
        this.appTypeId = appTypeId;
    }
}