package com.lyun.user.api.service;

import com.lyun.api.request.BaseRequest;
import com.lyun.api.response.APIResult;
import com.lyun.user.api.APIConstants;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LawWorldService {

    @POST(APIConstants.QUERY_LAW_WORLD_LIST)
    Observable<APIResult<List<Object>>> queryLawWorldList(@Body BaseRequest body);
}
