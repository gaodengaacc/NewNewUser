package com.lyun.user.model;

import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.RegisterVerifyPhoneBean;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 郑成裕 on 2017/2/15.
 */

public class RegisterVerifyPhoneModel extends Model {
    public Observable<APIResult> getSmsCode(String cardNo) {
        RegisterVerifyPhoneBean bean = new RegisterVerifyPhoneBean(cardNo);
        return API.auth.getSmsCode(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
