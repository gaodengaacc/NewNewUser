package com.lyun.user.model;

import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.StatisticsCardNoBean;
import com.lyun.user.api.response.StatisticsCardNoResponse;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 郑成裕 on 2017/2/23.
 */

public class StatisticsCardNoModel extends Model {
    public Observable<APIResult<StatisticsCardNoResponse>> getStatistics(String cardNo) {
        StatisticsCardNoBean bean = new StatisticsCardNoBean(cardNo);
        return API.auth.getStatistics(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
