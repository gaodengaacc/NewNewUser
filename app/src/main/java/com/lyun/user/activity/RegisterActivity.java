package com.lyun.user.activity;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityRegisterBinding;
import com.lyun.user.viewmodel.RegisterViewModel;
import com.lyun.user.viewmodel.watchdog.IRegisterViewModelCallbacks;

public class RegisterActivity extends GeneralToolbarActivity<ActivityRegisterBinding, RegisterViewModel> implements IRegisterViewModelCallbacks {

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_register;
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.title.set("快速注册");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @NonNull
    @Override
    protected RegisterViewModel createBodyViewModel() {
        return new RegisterViewModel()
                .setPropertyChangeListener(this);
    }

    @Override
    public void onRegisterSuccess(BaseObservable observableField, int fieldId) {
        finish();
    }

    @Override
    public void onRegisterFailed(ObservableField<Throwable> observableField, int fieldId) {
        Toast.makeText(this, observableField.get().getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPasswordBlank(BaseObservable observableField, int fieldId) {
        Toast.makeText(this, "请输入密码！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConfirmPasswordBlank(BaseObservable observableField, int fieldId) {
        Toast.makeText(this, "请确认密码！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPasswordSame(BaseObservable observableField, int fieldId) {
        Toast.makeText(this, "两次输入密码不同,请重新输入！", Toast.LENGTH_LONG).show();
    }
}
