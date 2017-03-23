package com.lyun.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // LeakCanary
        RefWatcher refWatcher = BaseApplication.getRefWatcher();
        refWatcher.watch(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 过滤按键动作
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        // 按返回键不结束activity
        moveTaskToBack(true);
        super.onBackPressed();
    }

}