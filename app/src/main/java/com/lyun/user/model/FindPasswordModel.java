package com.lyun.user.model;

import com.lyun.api.ErrorParser;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.FindPasswordBean;
import com.lyun.user.api.request.RegisterVerifyPhoneBean;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 郑成裕 on 2017/2/21.
 */

public class FindPasswordModel extends Model {
    public Observable<APIResult> getSmsCode(String cardNo,String status) {
        RegisterVerifyPhoneBean bean = new RegisterVerifyPhoneBean(cardNo,status);
        return API.auth.getSmsCode(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<APIResult> findPassword(String userName, String verification, String password) {
        FindPasswordBean bean = new FindPasswordBean(userName, verification, password);
        return API.auth.findPassword(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
