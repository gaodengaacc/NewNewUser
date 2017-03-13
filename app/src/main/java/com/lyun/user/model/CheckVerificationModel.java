package com.lyun.user.model;

import com.lyun.api.response.APIResult;
import com.lyun.api.ErrorParser;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.CheckVerificationBean;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 郑成裕 on 2017/2/20.
 */

public class CheckVerificationModel extends Model {
    public Observable<APIResult> checkVerification(String name, String verification) {
        CheckVerificationBean bean = new CheckVerificationBean(name, verification);
        return API.auth.checkVerification(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
