package com.lyun.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lyun.activity.BaseActivity;
import com.lyun.user.R;
import com.lyun.user.im.avchat.activity.AVChatActivity;

public class SplashActivity extends BaseActivity {
    private static int sleepTime = 3500;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(() -> {
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, AVChatActivity.class);
            startActivity(intent);
            finish();
        }, sleepTime);
    }

}
