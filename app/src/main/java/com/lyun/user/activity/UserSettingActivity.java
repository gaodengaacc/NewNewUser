package com.lyun.user.activity;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityUserSettingBinding;
import com.lyun.user.viewmodel.UserSettingViewModel;
import com.lyun.user.viewmodel.watchdog.IUserSettingViewModelCallbacks;

/**
 * @author Gordon
 * @since 2017/1/6
 * do()
 */

public class UserSettingActivity extends GeneralToolbarActivity<ActivityUserSettingBinding, UserSettingViewModel>
        implements IUserSettingViewModelCallbacks {

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_user_setting;
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("设置");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @NonNull
    @Override
    protected UserSettingViewModel createBodyViewModel() {
        return new UserSettingViewModel().setPropertyChangeListener(this);
    }

    @Override
    public void onNavigationModifyPassword(BaseObservable observableField, int fieldId) {
        startActivity(new Intent(this, FindPasswordActivity.class));
    }
}
