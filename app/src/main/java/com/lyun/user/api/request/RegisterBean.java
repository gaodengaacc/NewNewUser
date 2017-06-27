package com.lyun.user.api.request;

/**
 * Created by 郑成裕 on 2017/2/15.
 */

public class RegisterBean extends BaseRequestBean {
    private String name;
    private String password;

    public RegisterBean(String name, String password) {
        this.password = password;
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
