package com.lyun.user.model;

import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.LoginBean;
import com.lyun.user.api.response.LoginResponse;
import com.lyun.utils.MD5Util;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public class LoginModel extends Model {

    public Observable<LoginResponse> login(String username, String password) {
        LoginBean bean = new LoginBean(username, MD5Util.getStringMD5(password));
        return parseAPIObservable(API.auth.login(bean))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
