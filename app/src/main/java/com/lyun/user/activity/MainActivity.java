package com.lyun.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.KeyEvent;
import android.view.Window;

import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.adapter.MainPagerAdapter;
import com.lyun.user.databinding.ActivityMainBinding;
import com.lyun.user.fragment.HomeFragment;
import com.lyun.user.fragment.LawWorldFragment;
import com.lyun.user.fragment.ServiceCardFragment;
import com.lyun.user.fragment.UserCenterFragment;
import com.lyun.user.im.config.preference.UserPreferences;
import com.lyun.user.viewmodel.MainActivityViewModel;
import com.netease.nimlib.sdk.NIMClient;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MvvmActivity<ActivityMainBinding, MainActivityViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDarkMode(true, this);
    }

    @NonNull
    @Override
    protected MainActivityViewModel createViewModel() {
        UserPreferences.setNotificationToggle(false);
        NIMClient.toggleNotification(false);
        return new MainActivityViewModel(getPagerAdapter());
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (getIntent().getBooleanExtra("isFromResetPassword", false)) {
            ObservableNotifier.alwaysNotify(getActivityViewModel().currentItem, 0);
        }
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

    public PagerAdapter getPagerAdapter() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance());
        fragments.add(LawWorldFragment.newInstance());
        fragments.add(ServiceCardFragment.newInstance());
        fragments.add(UserCenterFragment.newInstance());
        return new MainPagerAdapter(this, getSupportFragmentManager(), fragments);
    }

    public void setStatusBarDarkMode(boolean darkmode, Activity activity) {
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
