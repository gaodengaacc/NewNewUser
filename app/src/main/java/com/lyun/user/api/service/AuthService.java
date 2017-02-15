package com.lyun.user.api.service;

import com.lyun.api.response.APIResult;
import com.lyun.user.api.APIConstants;
import com.lyun.user.api.request.LoginBean;
import com.lyun.user.api.request.RegisterBean;
import com.lyun.user.api.request.RegisterVerifyPhoneBean;
import com.lyun.user.api.response.LoginResponse;
import com.lyun.user.api.response.RegisterVerifyPhoneRespose;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 赵尉尉
 * @date 2016/12/20
 */

public interface AuthService {

    @POST(APIConstants.LOGIN)
    Observable<APIResult<LoginResponse>> login(@Body LoginBean body);

    @POST(APIConstants.REGISTERVERIFYPHONE)
    Observable<APIResult<RegisterVerifyPhoneRespose>> getSmsCode(@Body RegisterVerifyPhoneBean body);

    @POST(APIConstants.REGISTER)
    Observable<APIResult> register(@Body RegisterBean body);
}
