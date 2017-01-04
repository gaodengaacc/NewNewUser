package com.lyun.user.api.service;

import com.lyun.api.response.APIResult;
import com.lyun.http.LogInterceptor;
import com.lyun.user.api.API;
import com.lyun.user.api.request.LoginBean;
import com.lyun.user.api.response.LoginResponse;
import com.lyun.user.api.response.UserInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.functions.Consumer;

/**
 * Created by ZHAOWEIWEI on 2016/12/30.
 */
public class AuthServiceTest {

    private AuthService authService;

    @Before
    public void setUp() throws Exception {
        API.init("http://172.16.132.49:8080/lawcloudpeoplecenter/api/app/", new LogInterceptor());
        authService = API.auth;
    }

    @Test
    public void login() throws Exception {
        LoginBean bean = new LoginBean();
        bean.setName("13838502074");
        bean.setPassword("e10adc3949ba59abbe56e057f20f883e");
        authService
                .login(bean)
                .subscribe(new Consumer<APIResult<LoginResponse>>() {
                    @Override
                    public void accept(APIResult<LoginResponse> userInfoAPIResult) throws Exception {
                        Assert.assertNotNull(userInfoAPIResult);
                    }
                });
    }

}