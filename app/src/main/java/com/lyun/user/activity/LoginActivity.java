package com.lyun.user.activity;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityLoginBinding;
import com.lyun.user.viewmodel.LoginViewModel;
import com.lyun.user.viewmodel.watchdog.ILoginViewModelCallbacks;

public class LoginActivity extends GeneralToolbarActivity<ActivityLoginBinding, LoginViewModel> implements ILoginViewModelCallbacks {

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_login;
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("登录");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @NonNull
    @Override
    protected LoginViewModel createBodyViewModel() {
        return new LoginViewModel().setPropertyChangeListener(this);
    }

    @Override
    public void onNavigationRegister(BaseObservable observableField, int fieldId) {
        startActivity(new Intent(this, RegisterVerifyPhoneActivity.class));
    }

    @Override
    public void onNavigationFindPassword(BaseObservable observableField, int fieldId) {
        startActivity(new Intent(this, FindPasswordActivity.class));
    }

    @Override
    public void onLoginSuccess(BaseObservable observableField, int fieldId) {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }
}
