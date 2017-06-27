package com.lyun.user.api.service;

import com.lyun.api.request.BaseRequest;
import com.lyun.api.response.APIResult;
import com.lyun.user.api.APIConstants;
import com.lyun.user.api.response.FindLanguageResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ZHAOWEIWEI on 2017/2/22.
 */

public interface LanguageService {

    @POST(APIConstants.FIND_BY_LANGUAGE)
    Observable<APIResult<List<FindLanguageResponse>>> findByLanguage(@Body BaseRequest body);

}
