package com.lyun.user.activity;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityGuideBinding;
import com.lyun.user.viewmodel.GuideViewModel;
import com.lyun.user.viewmodel.watchdog.IGuideViewModelCallbacks;

/**
 * Created by 郑成裕 on 2017/3/14.
 */

public class GuideActivity extends MvvmActivity<ActivityGuideBinding, GuideViewModel> implements IGuideViewModelCallbacks {

    @NonNull
    @Override
    protected GuideViewModel createViewModel() {
        return new GuideViewModel(this).setPropertyChangeListener(this);
    }

    @NonNull
    @Override
    protected ViewModel getBodyViewModel() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void skipResult(BaseObservable observableField, int fieldId) {
        Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
