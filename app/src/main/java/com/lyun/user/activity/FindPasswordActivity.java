package com.lyun.user.activity;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityFindPasswordBinding;
import com.lyun.user.viewmodel.FindPasswordViewModel;
import com.lyun.user.viewmodel.watchdog.IFindPasswordViewModelCallbacks;

public class FindPasswordActivity extends GeneralToolbarActivity<ActivityFindPasswordBinding, FindPasswordViewModel>
        implements IFindPasswordViewModelCallbacks {
    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_find_password;
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("忘记密码");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @NonNull
    @Override
    protected FindPasswordViewModel createBodyViewModel() {
        return new FindPasswordViewModel().setPropertyChangeListener(this);
    }

    @Override
    public void onFindPasswordSuccess(BaseObservable observableField, int fieldId) {
        Toast.makeText(AppApplication.getInstance(), "修改成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFindPasswordResult(ObservableField<String> observableField, int fieldId) {
        Toast.makeText(AppApplication.getInstance(), observableField.get(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void progressDialogShow(ObservableBoolean observableField, int fieldId) {
        if (observableField.get())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }
}
