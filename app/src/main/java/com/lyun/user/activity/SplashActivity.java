package com.lyun.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.lyun.activity.BaseActivity;
import com.lyun.api.response.APIResult;
import com.lyun.user.R;
import com.lyun.user.api.API;
import com.lyun.user.api.request.LoginBean;
import com.lyun.user.api.response.UserInfo;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    private static int sleepTime = 3500;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, sleepTime);

        doAPIDemo();
    }

    private void doAPIDemo() {
        // 不处理返回结果
        API.auth.login(new LoginBean())
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe();

        // 只处理成功返回结果
        API.auth.login(new LoginBean())
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<APIResult<UserInfo>>() {
                    @Override
                    public void accept(APIResult<UserInfo> userInfoAPIResult) throws Exception {
                        Log.e("API demo", "只处理成功");
                    }
                });

        // 处理成功及失败返回结果
        API.auth.login(new LoginBean())
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<APIResult<UserInfo>>() {
                    @Override
                    public void accept(APIResult<UserInfo> userInfoAPIResult) throws Exception {
                        Log.e("API demo", "处理成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("API demo", "处理失败");
                    }
                });


        // 处理成功及失败返回结果及成功失败都走的方法
        API.auth.login(new LoginBean())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<APIResult<UserInfo>>() {
                    @Override
                    public void accept(APIResult<UserInfo> userInfoAPIResult) throws Exception {
                        Log.e("API demo", "处理成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("API demo", "处理失败");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e("API demo", "成功失败都走的方法");
                    }
                });

        API.auth.login(new LoginBean())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<APIResult<UserInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("API demo", "Observer onSubscribe");
                    }

                    @Override
                    public void onNext(APIResult<UserInfo> value) {
                        Log.e("API demo", "Observer onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("API demo", "Observer onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("API demo", "Observer onComplete");
                    }
                });
    }
}
