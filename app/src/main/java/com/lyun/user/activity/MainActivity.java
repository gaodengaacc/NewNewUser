package com.lyun.user.activity;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityMainBinding;
import com.lyun.user.viewmodel.MainActivityViewModel;

public class MainActivity extends MvvmActivity<ActivityMainBinding, MainActivityViewModel> {

    @NonNull
    @Override
    protected MainActivityViewModel createViewModel() {
        return new MainActivityViewModel(getActivityViewDataBinding().mainContainer, getSupportFragmentManager());
    }

    @NonNull
    @Override
    protected ViewModel getBodyViewModel() {
        return null;
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
            ObservableNotifier.alwaysNotify(getActivityViewModel().selectIndex, 0);
        }
    }
}
