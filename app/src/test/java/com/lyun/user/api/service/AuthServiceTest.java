package com.lyun.user.api.service;

import com.lyun.http.LogInterceptor;
import com.lyun.user.api.API;
import com.lyun.user.api.request.LoginBean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ZHAOWEIWEI on 2016/12/30.
 */
public class AuthServiceTest {

    private AuthService authService;

    @Before
    public void setUp() throws Exception {
        //API.init("http://172.16.157.147:8080/lytapp/apiDesc/", new LogInterceptor());
        authService = API.auth;
    }

    @Test
    public void login() throws Exception {
        LoginBean bean = new LoginBean("13838502074", "e10adc3949ba59abbe56e057f20f883e");
        authService
                .login(bean)
                .subscribe(userInfoAPIResult -> {
                    Assert.assertNotNull(userInfoAPIResult);
                });
    }

}