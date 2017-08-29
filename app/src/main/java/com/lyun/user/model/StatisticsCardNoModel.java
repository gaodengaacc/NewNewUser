package com.lyun.user.model;

import com.lyun.api.ErrorParser;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.BaseRequestBean;
import com.lyun.user.api.response.StatisticsCardNoResponse;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 郑成裕 on 2017/2/23.
 */

public class StatisticsCardNoModel extends Model {
    public Observable<APIResult<StatisticsCardNoResponse>> getStatistics(String cardNo) {
        return API.auth.getStatistics(new BaseRequestBean())
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
