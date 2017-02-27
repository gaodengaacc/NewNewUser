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
import com.lyun.user.databinding.ActivityRegisterVerifyPhoneBinding;
import com.lyun.user.viewmodel.RegisterVerifyPhoneViewModel;
import com.lyun.user.viewmodel.watchdog.IRegisterVerifyPhoneViewModelCallbacks;

public class RegisterVerifyPhoneActivity extends GeneralToolbarActivity<ActivityRegisterVerifyPhoneBinding, RegisterVerifyPhoneViewModel>
        implements IRegisterVerifyPhoneViewModelCallbacks {

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_register_verify_phone;
    }

    @NonNull
    @Override
    protected RegisterVerifyPhoneViewModel createBodyViewModel() {
        return new RegisterVerifyPhoneViewModel()
                .setPropertyChangeListener(this);
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.title.set("快速注册");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @Override
    public void onVerifySuccess(ObservableField<Intent> observableField, int fieldId) {
        startActivity(observableField.get());
        finish();
    }

    @Override
    public void onNumberBlank(BaseObservable observableField, int fieldId) {
        Toast.makeText(this, "请输入手机号!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNumberWrong(BaseObservable observableField, int fieldId) {
        Toast.makeText(this, "请输入正确的手机号!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSmsCodeBlank(BaseObservable observableField, int fieldId) {
        Toast.makeText(this, "请输入验证码!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerifyResult(ObservableField<String> observableField, int fieldId) {
        Toast.makeText(this, observableField.get(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(BaseObservable observableField, int fieldId) {
        Toast.makeText(this, "验证成功!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void progressDialogShow(ObservableBoolean observableField, int fieldId) {
        if (observableField.get())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }
}
