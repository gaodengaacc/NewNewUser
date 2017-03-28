package com.lyun.user.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.lyun.activity.BaseActivity;
import com.lyun.user.Account;
import com.lyun.user.AppIntent;
import com.lyun.user.R;
import com.lyun.user.im.avchat.AVChatProfile;
import com.lyun.user.model.LanguageModel;
import com.lyun.utils.GlideUtils;
import com.lyun.utils.L;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends BaseActivity {
    private static int sleepTime = 3500;
    private Handler mHandler;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GlideUtils.showImage(this, (ImageView) findViewById(R.id.bg_splash), R.mipmap.bg_splash);

        checkPermission();

        if (Account.preference().isLogin())
            new LanguageModel().updateLanguages();
        if (mHandler == null)
            mHandler = new Handler();
        mHandler.postDelayed(() -> {
            // 判断是否首次启动
            if (!Account.preference().isFirstSplash()) {
                // Account.preference().setFirstSplash(true);
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

    protected final int REQUEST_PERMISSION = 0x001;

    @AfterPermissionGranted(REQUEST_PERMISSION)
    public void checkPermission() {
        if (EasyPermissions.hasPermissions(this,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        } else {
            EasyPermissions.requestPermissions(this, "为保证app正常运行，需要这些权限",
                    REQUEST_PERMISSION,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
