package com.lyun.user.api.service;

import com.lyun.api.response.APIResult;
import com.lyun.user.api.APIConstants;
import com.lyun.user.api.request.GenerateOrderRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ZHAOWEIWEI on 2017/2/16.
 */

public interface TranslationOrderService {

    @POST(APIConstants.GENERATE_TRANSLATION_ORDER)
    Observable<APIResult<String>> generateOrder(@Body GenerateOrderRequest orderRequest);

}
