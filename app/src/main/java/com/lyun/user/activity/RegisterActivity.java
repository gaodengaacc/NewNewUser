package com.lyun.user.activity;

import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityRegisterBinding;
import com.lyun.user.viewmodel.RegisterViewModel;

public class RegisterActivity extends GeneralToolbarActivity<ActivityRegisterBinding, RegisterViewModel> {

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_register;
    }

    @NonNull
    @Override
    protected RegisterViewModel createBodyViewModel() {
        return new RegisterViewModel()
                .setPropertyChangeListener(this);
    }
}
