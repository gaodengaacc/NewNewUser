package com.lyun.user.activity;

import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityLoginBinding;
import com.lyun.user.viewmodel.LoginViewModel;

public class LoginActivity extends GeneralToolbarActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_login;
    }

    @NonNull
    @Override
    protected LoginViewModel createBodyViewModel() {
        LoginViewModel model = new LoginViewModel(getTitleViewDataBinding().getMvvm());
        return model;
    }

}
