package com.lyun.user.activity;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
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
        viewModel.title.set("找回密码");
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
        finish();
    }
}
