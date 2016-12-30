package com.lyun.user.api.service;

import com.lyun.user.api.API;
import com.lyun.user.api.request.LoginBean;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by ZHAOWEIWEI on 2016/12/30.
 */
public class AuthServiceTest {

    private AuthService authService;

    @Before
    public void setUp() throws Exception {
        API.init("http://172.16.252.148:8080/lawcloudpeoplecenter/api/");
        authService = API.auth;
    }

    @Test
    public void login() throws Exception {
        LoginBean bean = new LoginBean();
        bean.setName("15958331916");
        bean.setPassword("e10adc3949ba59abbe56e057f20f88e");
        authService
                .login(bean)
                .subscribe();
    }

}