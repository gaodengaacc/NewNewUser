package com.lyun.user.activity;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityResetPasswordBinding;
import com.lyun.user.viewmodel.ResetPasswordViewModel;
import com.lyun.user.viewmodel.watchdog.IResetPasswordViewModelCallbacks;

/**
 * @author Gordon
 * @since 2017/2/15
 * do()
 */

public class ResetPasswordActivity extends GeneralToolbarActivity<ActivityResetPasswordBinding, ResetPasswordViewModel> implements IResetPasswordViewModelCallbacks {


    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("修改密码");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_reset_password;
    }

    @NonNull
    @Override
    protected ResetPasswordViewModel createBodyViewModel() {
        return new ResetPasswordViewModel().setPropertyChangeListener(this);
    }

    @Override
    public void onResetPasswordResult(ObservableField<String> observableField, int fieldId) {
        Toast.makeText(this, observableField.get(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPasswordBlank(BaseObservable observableField, int fieldId) {
        Toast.makeText(this, "请输入原密码!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNewPasswordBlank(BaseObservable observableField, int fieldId) {
        Toast.makeText(this, "请输入新密码!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConfirmPasswordBlank(BaseObservable observableField, int fieldId) {
        Toast.makeText(this, "请确认新密码!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLogout(BaseObservable observableField, int fieldId) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void progressDialogShow(ObservableBoolean observableField, int fieldId) {
        if (observableField.get())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }

}
