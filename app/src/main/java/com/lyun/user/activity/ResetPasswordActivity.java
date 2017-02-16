package com.lyun.user.activity;

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

public class ResetPasswordActivity extends GeneralToolbarActivity<ActivityResetPasswordBinding,ResetPasswordViewModel> implements IResetPasswordViewModelCallbacks{


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
    public void progressDialogShow(ObservableBoolean observableField, int fieldId) {
        if(observableField.get())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }
}
