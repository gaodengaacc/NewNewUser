package com.lyun.user.activity;

import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityMainBinding;
import com.lyun.user.viewmodel.MainActivityViewModel;

public class MainActivity extends MvvmActivity<ActivityMainBinding, MainActivityViewModel> {

    @NonNull
    @Override
    protected MainActivityViewModel createViewModel() {
        return new MainActivityViewModel(this, getActivityViewDataBinding().mainContainer, getSupportFragmentManager());
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
}
