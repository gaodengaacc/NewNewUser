package com.lyun.user.model;

import com.lyun.api.response.APIResult;
import com.lyun.http.OnError;
import com.lyun.http.OnSuccess;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.LoginBean;
import com.lyun.user.api.response.LoginResponse;
import com.lyun.utils.L;
import com.lyun.utils.MD5Util;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public class LoginModel extends Model {

    public void login(String username, String password) {
        LoginBean bean = new LoginBean(username, MD5Util.getStringMD5(password));
        API.auth.login(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnSuccess<APIResult<LoginResponse>>() {
                    @Override
                    public void onSuccess(APIResult<LoginResponse> loginResponseAPIResult) {

                    }
                }, new OnError() {
                    @Override
                    public void onError(Throwable t) {
                        L.e("tag", t);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }

}
