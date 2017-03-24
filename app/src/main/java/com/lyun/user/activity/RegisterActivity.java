package com.lyun.user.activity;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityRegisterBinding;
import com.lyun.user.viewmodel.RegisterViewModel;
import com.lyun.user.viewmodel.watchdog.IRegisterViewModelCallbacks;

public class RegisterActivity extends GeneralToolbarActivity<ActivityRegisterBinding, RegisterViewModel> implements IRegisterViewModelCallbacks {
    private Intent intent = new Intent();
    private Bundle bundle = new Bundle();

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
        intent = getIntent();
        bundle = intent.getExtras();
        return new RegisterViewModel(bundle)
                .setPropertyChangeListener(this);
    }

    @Override
    public void onRegisterSuccess(BaseObservable observableField, int fieldId) {
        Toast.makeText(AppApplication.getInstance(), "注册成功", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onRegisterFailed(ObservableField<Throwable> observableField, int fieldId) {
        Toast.makeText(AppApplication.getInstance(), observableField.get().getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void progressDialogShow(ObservableBoolean observableField, int fieldId) {
        if (observableField.get())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }

    @Override
    public void onRegisterResult(ObservableField<String> observableField, int fieldId) {
        Toast.makeText(AppApplication.getInstance(), observableField.get(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAgreementResult(ObservableField<Intent> observableField, int fieldId) {
        startActivity(observableField.get());
    }
}
