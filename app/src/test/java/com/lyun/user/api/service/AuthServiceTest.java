package com.lyun.user.api.service;

import com.lyun.api.response.APIResult;
import com.lyun.http.APIClient;
import com.lyun.user.api.API;
import com.lyun.user.api.request.LoginBean;
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
        API.init("http://172.16.252.148:8080/lawcloudpeoplecenter/api/");
        authService = API.auth;
    }

    @Test
    public void login() throws Exception {
        LoginBean bean = new LoginBean();
        bean.setName("15958331916");
        bean.setPassword("e10adc3949ba59abbe56e057f20f88e");
        APIClient client = APIClient.init("http://172.16.252.148:8080/lawcloudpeoplecenter/api/");
        AuthService authService = client.retrofit().create(AuthService.class);
        authService
                .login(bean)
                .subscribe();
    }

}