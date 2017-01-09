package com.lyun.user.model;

import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.LoginBean;
import com.lyun.user.api.response.LoginResponse;
import com.lyun.utils.L;
import com.lyun.utils.MD5Util;

import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public class LoginModel extends Model {

    public Observable<APIResult<LoginResponse>> login(String username, String password) {
        LoginBean bean = new LoginBean(username, MD5Util.getStringMD5(password));
        return API.auth.login(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

}
