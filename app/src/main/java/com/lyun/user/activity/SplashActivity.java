package com.lyun.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lyun.activity.BaseActivity;
import com.lyun.user.Account;
import com.lyun.user.R;
import com.lyun.user.model.HomeFragmentModel;

public class SplashActivity extends BaseActivity {
    private static int sleepTime = 3500;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(() -> {
            Intent intent = new Intent();
            if (Account.preference().isLogin()) {
                intent.setClass(SplashActivity.this, MainActivity.class);
                new HomeFragmentModel().setFindByLanguage();
            } else {
                intent.setClass(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, sleepTime);
    }

}
