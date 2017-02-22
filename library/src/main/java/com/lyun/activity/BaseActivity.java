package com.lyun.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.lyun.BaseApplication;
import com.lyun.widget.dialog.ProgressBarDialog;
import com.squareup.leakcanary.RefWatcher;


/**
 * 任何Activity必须继承自该Activity
 *
 * @author 赵尉尉
 * @since 2015/5/13 16:16
 */
public class BaseActivity extends FragmentActivity  {

    protected ProgressBarDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
        // 友盟统计
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // LeakCanary
        RefWatcher refWatcher = BaseApplication.getRefWatcher();
        refWatcher.watch(this);
    }

}