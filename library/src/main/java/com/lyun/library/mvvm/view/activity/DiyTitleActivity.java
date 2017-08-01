package com.lyun.library.mvvm.view.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;

import com.lyun.library.BR;
import com.lyun.library.R;
import com.lyun.library.databinding.ActivityDiyTitleBinding;
import com.lyun.library.mvvm.viewmodel.DiyTitleViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;

public abstract class DiyTitleActivity<TVDB extends ViewDataBinding, CVDB extends ViewDataBinding, TVM extends ViewModel, CVM extends ViewModel>
        extends MvvmActivity<ActivityDiyTitleBinding, DiyTitleViewModel>
        implements ViewStubCompat.OnInflateListener {

    protected TVDB mTitleViewDataBinding;
    protected CVDB mBodyViewDataBinding;
    protected TVM mTitleViewModel;
    protected CVM mBodyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityViewDataBinding().generalToolbarViewStub.setOnInflateListener(this);
        getActivityViewDataBinding().generalToolbarViewStub.setLayoutResource(getTitleLayoutId());
        getActivityViewDataBinding().generalToolbarViewStub.inflate();

        getActivityViewDataBinding().generalToolbarContentViewStub.setOnInflateListener(this);
        getActivityViewDataBinding().generalToolbarContentViewStub.setLayoutResource(getBodyLayoutId());
        getActivityViewDataBinding().generalToolbarContentViewStub.inflate();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getBodyViewModel() != null)
            getBodyViewModel().onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (getBodyViewModel() != null)
            getBodyViewModel().onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getBodyViewModel() != null)
            getBodyViewModel().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyViewDataBinding(mTitleViewDataBinding);
        destroyViewDataBinding(mBodyViewDataBinding);
        if (getBodyViewModel() != null)
            getBodyViewModel().onDestroy();
    }

    @NonNull
    @Override
    protected DiyTitleViewModel createViewModel() {
        return new DiyTitleViewModel().setPropertyChangeListener(this);
    }

    @NonNull
    protected CVM getBodyViewModel() {
        return mBodyViewModel;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_diy_title;
    }

    @LayoutRes
    protected abstract int getTitleLayoutId();

    @NonNull
    protected abstract TVM createTitleViewModel();

    @LayoutRes
    protected abstract int getBodyLayoutId();

    @NonNull
    protected abstract CVM createBodyViewModel();

    @Override
    public void onInflate(ViewStubCompat stub, View inflated) {
        if (stub.getLayoutResource() == getTitleLayoutId()) {
            mTitleViewModel = createTitleViewModel();
            mTitleViewDataBinding = DataBindingUtil.bind(inflated);
            mTitleViewDataBinding.setVariable(BR.mvvm, registerViewModel(mTitleViewModel));
        } else if (stub.getLayoutResource() == getBodyLayoutId()) {
            mBodyViewModel = createBodyViewModel();
            mBodyViewDataBinding = DataBindingUtil.bind(inflated);
            mBodyViewDataBinding.setVariable(BR.mvvm, registerViewModel(mBodyViewModel));
        }
    }

    protected TVDB getTitleViewDataBinding() {
        return mTitleViewDataBinding;
    }

    protected CVDB getBodyViewDataBinding() {
        return mBodyViewDataBinding;
    }

}
