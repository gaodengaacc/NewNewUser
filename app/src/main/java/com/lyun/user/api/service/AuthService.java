package com.lyun.user.api.service;

import com.lyun.api.response.APIPageResult;
import com.lyun.api.response.APIResult;
import com.lyun.user.api.APIConstants;
import com.lyun.user.api.request.CheckVerificationBean;
import com.lyun.user.api.request.FindPasswordBean;
import com.lyun.user.api.request.LoginBean;
import com.lyun.user.api.request.RegisterBean;
import com.lyun.user.api.request.RegisterVerifyPhoneBean;
import com.lyun.user.api.request.RemainingTimeBean;
import com.lyun.user.api.request.ResetPasswordBean;
import com.lyun.user.api.request.StatisticsCardNoBean;
import com.lyun.user.api.request.WalletChargeBean;
import com.lyun.user.api.request.WalletChargeRecorderBean;
import com.lyun.user.api.request.WalletChargeUpdateStateBean;
import com.lyun.user.api.response.LoginResponse;
import com.lyun.user.api.response.StatisticsCardNoResponse;
import com.lyun.user.api.response.WalletChargeAliPayResponse;
import com.lyun.user.api.response.StatisticsCardNoResponse;
import com.lyun.user.api.response.WalletChargeRecorderResponse;
import com.lyun.user.api.response.WalletChargeWxPayResponse;

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

    @POST(APIConstants.FIND_PASSWORD)
    Observable<APIResult> findPassword(@Body FindPasswordBean body);

    @POST(APIConstants.REMAINING_TIME)
    Observable<APIResult> getRemainingTime(@Body RemainingTimeBean body);

   //充值接口
    @POST(APIConstants.CHARGE_PAY)
    Observable<APIResult<WalletChargeAliPayResponse>> getAliChargeOrder(@Body WalletChargeBean body);
    @POST(APIConstants.CHARGE_PAY)
    Observable<APIResult<WalletChargeWxPayResponse>> getWxChargeOrder(@Body WalletChargeBean body);
    @POST(APIConstants.CHARGE_UPDATE)
    Observable<APIResult> setChargeOrderUpdate(@Body WalletChargeUpdateStateBean body);
    @POST(APIConstants.CHARGE_RECORDER)
    Observable<APIResult<APIPageResult<List<WalletChargeRecorderResponse>>>> getChargeRecorder(@Body WalletChargeRecorderBean body);

    @POST(APIConstants.STATISTICS_CARDNO)
    Observable<APIResult<StatisticsCardNoResponse>> getStatistics(@Body StatisticsCardNoBean body);
}
