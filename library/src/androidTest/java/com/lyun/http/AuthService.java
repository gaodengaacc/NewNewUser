package com.lyun.http;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ZHAOWEIWEI on 2016/12/15.
 */

public interface AuthService {
    @POST("/api/app-login-service.json")
    Observable<APIResult<UserInfo>> login(@Body LoginBean body);
}
