package com.lyun.http;

import android.util.Log;

import com.google.gson.Gson;

import org.junit.Test;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZHAOWEIWEI on 2016/12/16.
 */
public class APIClientTest {

    @Test
    public void retrofit() throws Exception {
        APIClient.init("https://lyt.law-cloud.com.cn:8444");
        AuthService authService = APIClient.client()
                .retrofit()
                .create(AuthService.class);
        LoginBean bean = new LoginBean();
        authService.login(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<APIResult<UserInfo>>() {
                    @Override
                    public void accept(APIResult<UserInfo> userInfoAPIResult) throws Exception {
                        Log.e("result",new Gson().toJson(userInfoAPIResult));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("result",new Gson().toJson(throwable));
                    }
                });
    }

}