package com.lyun.user.api.service;

import com.lyun.api.response.APIPageResult;
import com.lyun.api.response.APIResult;
import com.lyun.user.api.APIConstants;
import com.lyun.user.api.request.LawWorldRequest;
import com.lyun.user.api.response.LawWorldResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LawWorldService {

    @POST(APIConstants.QUERY_LAW_WORLD_LIST)
    Observable<APIResult<APIPageResult<List<LawWorldResponse>>>> queryLawWorldList(@Body LawWorldRequest body);
}
