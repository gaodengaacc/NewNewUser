package com.lyun.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.lyun.BaseApplication;
import com.squareup.leakcanary.RefWatcher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 任何Activity必须继承自该Activity
 *
 * @author 赵尉尉
 * @since 2015/5/13 16:16
 */
public class BaseActivity extends AppCompatActivity {

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

    public void setStatusBarDarkMode(boolean darkmode) {
        setMiuiStatusBarDarkMode(darkmode, this);
    }

    public void setMiuiStatusBarDarkMode(boolean darkmode, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}