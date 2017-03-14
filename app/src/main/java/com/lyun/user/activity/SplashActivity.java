package com.lyun.user.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.lyun.activity.BaseActivity;
import com.lyun.user.Account;
import com.lyun.user.AppIntent;
import com.lyun.user.R;
import com.lyun.user.model.LanguageModel;
import com.lyun.utils.GlideUtils;

public class SplashActivity extends BaseActivity {
    private static int sleepTime = 3500;
    private Handler mHandler;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GlideUtils.showImage(this, (ImageView) findViewById(R.id.bg_splash), R.mipmap.bg_splash);
        preferences = getSharedPreferences("lytApp", Context.MODE_PRIVATE);

        if (Account.preference().isLogin())
            new LanguageModel().updateLanguages();
        if (mHandler == null)
            mHandler = new Handler();
        mHandler.postDelayed(() -> {
            //判断是否首次启动
            if (preferences.getBoolean("firstSplash", true)) {
                editor = preferences.edit();
                // 将登录标志位设置为false，下次登录时不在显示首次登录界面
                editor.putBoolean("firstSplash", false);
                editor.commit();
                intent = new Intent(AppIntent.ACTION_GUIDE);

            } else {
                if (Account.preference().isLogin()) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
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
