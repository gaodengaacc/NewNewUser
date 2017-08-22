package com.lyun.user.model;

import com.lyun.api.ErrorParser;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.RegisterBean;
import com.lyun.user.api.request.ThirdLoginRegisterBean;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 郑成裕 on 2017/2/15.
 */

public class RegisterModel extends Model {
    public Observable<APIResult> register(String username, String password) {
        RegisterBean bean = new RegisterBean(username, password);
        return API.auth.register(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
    public Observable<APIResult> registerThird(String userId,String openId,String loginType, String password) {
        return API.auth.thirdRegister(new ThirdLoginRegisterBean(userId,openId,loginType,password))
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
