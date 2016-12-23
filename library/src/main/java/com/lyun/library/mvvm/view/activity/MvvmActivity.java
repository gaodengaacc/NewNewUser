package com.lyun.library.mvvm.view.activity;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.lyun.activity.BaseActivity;
import com.lyun.library.BR;
import com.lyun.library.mvvm.viewmodel.ViewModel;

public abstract class MvvmActivity<VDB extends ViewDataBinding, VM extends ViewModel> extends BaseActivity {

    private VDB mActivityViewDataBinding;
    protected VM mActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityViewDataBinding = DataBindingUtil.setContentView(this, getContentLayoutId());
        mActivityViewModel = createViewModel();
        registerViewModel(mActivityViewModel);
        mActivityViewDataBinding.setVariable(BR.mvvm, mActivityViewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyViewDataBinding(mActivityViewDataBinding);
    }

    protected void destroyViewDataBinding(ViewDataBinding viewDataBinding) {
        viewDataBinding.unbind();
        viewDataBinding.executePendingBindings();
    }

    protected <T extends ViewModel> T registerViewModel(T viewModel) {
        viewModel.activityFinish.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                mActivityViewModel.activityFinish.set(false);
                finish();
            }
        });
        return viewModel;
    }

    @NonNull
    protected abstract VM createViewModel();

    @LayoutRes
    protected abstract int getContentLayoutId();

    protected VM getActivityViewModel() {
        return mActivityViewModel;
    }

    protected VDB getActivityViewDataBinding() {
        return mActivityViewDataBinding;
    }
}
