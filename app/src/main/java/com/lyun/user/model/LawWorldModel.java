package com.lyun.user.model;

import com.lyun.api.ErrorParser;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.BaseRequestBean;
import com.lyun.user.api.response.LawWorldResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class LawWorldModel extends Model {

    public Observable<APIResult<List<LawWorldResponse>>> queryLawyerList(int page){
        return API.lawWorld.queryLawWorldList(new BaseRequestBean())
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
