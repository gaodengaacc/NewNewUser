package com.lyun.user.api.request;

import com.lyun.api.request.BaseRequest;

/**
 *
 * @author 赵尉尉
 * @date 2016/12/20
 */

public class LoginBean extends BaseRequest {

    /**
     * password : e10adc3949ba59abbe56e057f20f883e
     * userName : 13838502074
     * userType : 0
     * appId : 1001
     */

    private String password = "e10adc3949ba59abbe56e057f20f883e";
    private String userName = "13838502074";
    private int userType = 0;
    private int appId = 1001;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }
}
