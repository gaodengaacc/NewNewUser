package com.lyun.user.api.request;

/**
 * @author 赵尉尉
 * @date 2016/12/20
 */

public class LoginBean extends BaseRequestBean {

    private String password;
    private String name;

    public LoginBean(String name, String password) {
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
