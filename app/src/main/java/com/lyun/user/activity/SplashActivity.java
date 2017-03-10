package com.lyun.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.lyun.activity.BaseActivity;
import com.lyun.user.Account;
import com.lyun.user.R;
import com.lyun.user.model.LanguageModel;
import com.lyun.utils.GlideUtils;

public class SplashActivity extends BaseActivity {
    private static int sleepTime = 3500;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GlideUtils.showImage(this, (ImageView) findViewById(R.id.bg_splash),R.mipmap.bg_splash);
        if(Account.preference().isLogin())
        new LanguageModel().updateLanguages();
        if (mHandler == null)
            mHandler = new Handler();
        mHandler.postDelayed(() -> {
            Intent intent = new Intent();
            if (Account.preference().isLogin()) {
                intent.setClass(SplashActivity.this, MainActivity.class);
            } else {
                intent.setClass(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, sleepTime);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}
