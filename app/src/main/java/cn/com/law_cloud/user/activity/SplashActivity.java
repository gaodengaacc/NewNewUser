package cn.com.law_cloud.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.com.law_cloud.activity.BaseActivity;
import cn.com.law_cloud.user.R;

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
    }
}
