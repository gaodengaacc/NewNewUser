package com.lyun.user.activity;

import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityUserSettingBinding;
import com.lyun.user.viewmodel.UserSettingViewModel;

/**
 * @author Gordon
 * @since 2017/1/6
 * do()
 */

public class UserSettingActivity extends GeneralToolbarActivity<ActivityUserSettingBinding,UserSettingViewModel> {
    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_user_setting;
    }

    @NonNull
    @Override
    protected UserSettingViewModel createBodyViewModel() {
        return new UserSettingViewModel(getTitleViewDataBinding().getMvvm());
    }
}
