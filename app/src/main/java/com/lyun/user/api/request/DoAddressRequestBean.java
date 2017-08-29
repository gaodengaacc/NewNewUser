package com.lyun.user.api.request;

/**
 * @author Gordon
 * @since 2017/8/25
 * do()
 */

public class DoAddressRequestBean extends BaseRequestBean {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    public DoAddressRequestBean(String id){
        this.id = id;
    }
}
