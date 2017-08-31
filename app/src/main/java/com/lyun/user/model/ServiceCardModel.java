package com.lyun.user.model;

import com.lyun.api.ErrorParser;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.BaseRequestBean;
import com.lyun.user.api.response.MyServiceCardResponse;
import com.lyun.user.api.response.ServiceCardResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ServiceCardModel extends Model {

    public Observable<APIResult<List<ServiceCardResponse>>> queryServiceCardList(){
        return API.serviceCard.queryServiceCardList(new BaseRequestBean())
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<List<MyServiceCardResponse>> queryMyServiceCardList() {
        return parseAPIObservable(API.serviceCard.queryMyServiceCardList(new BaseRequestBean())
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
