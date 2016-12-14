package cn.com.law_cloud.user.activity;

import android.content.Intent;
import android.os.Bundle;

import cn.com.law_cloud.activity.BaseActivity;
import cn.com.law_cloud.user.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startActivity(new Intent(this,MainActivity.class));
    }
}
