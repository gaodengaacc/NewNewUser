package com.lyun.library.mvvm.view.activity;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.activity.BaseActivity;
import com.lyun.library.BR;
import com.lyun.library.mvvm.observable.ObservableActivity;
import com.lyun.library.mvvm.observable.ObservableToast;
import com.lyun.library.mvvm.observable.PropertyChangedCallback;
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
        viewModel.getActivity().addOnPropertyChangedCallback(new PropertyChangedCallback<ObservableActivity>() {
            @Override
            public void callback(ObservableActivity observable, int fieldId) {
                if (fieldId == BR.finish) {
                    finish();
                } else if (fieldId == BR.startActivity) {
                    startActivity(observable.getStartActivity().get());
                }
            }
        });
        viewModel.getToast().addOnPropertyChangedCallback(new PropertyChangedCallback<ObservableToast>() {
            @Override
            public void callback(ObservableToast observable, int fieldId) {
                Toast.makeText(getApplicationContext(), observable.getText(), observable.getDuration()).show();
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
