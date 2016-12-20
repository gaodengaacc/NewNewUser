package com.lyun.user.api.service;

import com.lyun.api.response.APIResult;
import com.lyun.user.api.request.LoginBean;
import com.lyun.user.api.response.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 赵尉尉
 * @date 2016/12/20
 */

public interface AuthService {

    @POST("/api/app-login-service.json")
    Observable<APIResult<UserInfo>> login(@Body LoginBean body);

}
