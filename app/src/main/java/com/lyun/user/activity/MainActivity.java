package com.lyun.user.activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityMainBinding;
import com.lyun.user.viewmodel.MainActivityViewModel;
public class MainActivity extends MvvmActivity<ActivityMainBinding,MainActivityViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = getActivityViewDataBinding();
        MainActivityViewModel viewModel = getActivityViewModel();
        viewModel.viewPage.set(activityMainBinding.mainContainer);
        viewModel.setFragmentManager(getSupportFragmentManager());
        viewModel.init();
    }

    @NonNull
    @Override
    protected MainActivityViewModel createViewModel() {
        return new MainActivityViewModel(this);
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
