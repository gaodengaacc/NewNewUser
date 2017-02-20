package com.lyun.user.api.service;

import com.lyun.api.request.BaseRequest;
import com.lyun.api.response.APIResult;
import com.lyun.user.api.APIConstants;
import com.lyun.user.api.request.CheckVerificationBean;
import com.lyun.user.api.request.FindByLanguageBean;
import com.lyun.user.api.request.LoginBean;
import com.lyun.user.api.request.RegisterBean;
import com.lyun.user.api.request.RegisterVerifyPhoneBean;
import com.lyun.user.api.request.ResetPasswordBean;
import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.user.api.response.LoginResponse;

import java.util.List;

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
    Observable<APIResult> getSmsCode(@Body RegisterVerifyPhoneBean body);

    @POST(APIConstants.CHECK_VERIFICATION)
    Observable<APIResult> checkVerification(@Body CheckVerificationBean body);

    @POST(APIConstants.REGISTER)
    Observable<APIResult> register(@Body RegisterBean body);

    @POST(APIConstants.RESET_PASSWORD)
    Observable<APIResult> resetPassword(@Body ResetPasswordBean body);

    @POST(APIConstants.FIND_BY_LANGUAGE)
    Observable<APIResult<List<FindLanguageResponse>>> findByLanguage(@Body BaseRequest body);
}
