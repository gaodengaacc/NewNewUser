package com.lyun.user.activity;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityAgreementBinding;
import com.lyun.user.viewmodel.AgreementViewModel;
import com.lyun.user.viewmodel.watchdog.IAgreementViewModelCallbacks;

/**
 * Created by 郑成裕 on 2017/3/13.
 */

public class AgreementActivity extends MvvmActivity<ActivityAgreementBinding, AgreementViewModel> implements IAgreementViewModelCallbacks {
    private Intent intent = new Intent();
    private Bundle bundle = new Bundle();

    @NonNull
    @Override
    protected AgreementViewModel createViewModel() {
        intent = getIntent();
        bundle = intent.getExtras();
        return new AgreementViewModel(bundle).setPropertyChangeListener(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_agreement;
    }

    @Override
    public void backResult(BaseObservable observableField, int fieldId) {
        finish();
    }
}
