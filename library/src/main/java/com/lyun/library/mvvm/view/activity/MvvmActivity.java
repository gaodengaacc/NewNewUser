package com.lyun.library.mvvm.view.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.activity.BaseActivity;
import com.lyun.library.BR;
import com.lyun.library.mvvm.observable.ObservableActivity;
import com.lyun.library.mvvm.observable.ObservableProgressDialog;
import com.lyun.library.mvvm.observable.ObservableToast;
import com.lyun.library.mvvm.observable.PropertyChangedCallback;
import com.lyun.library.mvvm.viewmodel.ProgressBarDialogViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;

public abstract class MvvmActivity<VDB extends ViewDataBinding, VM extends ViewModel> extends BaseActivity {

    private VDB mActivityViewDataBinding;
    protected VM mActivityViewModel;
    protected ProgressBarDialogViewModel dialogViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityViewDataBinding = DataBindingUtil.setContentView(this, getContentLayoutId());
        mActivityViewModel = createViewModel();
        registerViewModel(mActivityViewModel);
        mActivityViewDataBinding.setVariable(BR.mvvm, mActivityViewModel);
        dialogViewModel = new ProgressBarDialogViewModel(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mActivityViewModel != null)
            mActivityViewModel.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mActivityViewModel != null)
            mActivityViewModel.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mActivityViewModel != null)
            mActivityViewModel.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyViewDataBinding(mActivityViewDataBinding);
        if (mActivityViewModel != null)
            mActivityViewModel.onDestroy();
    }

    protected void destroyViewDataBinding(ViewDataBinding viewDataBinding) {
        viewDataBinding.unbind();
        viewDataBinding.executePendingBindings();
    }

    @Deprecated
    protected <T extends ViewModel> T registerViewModel(T viewModel) {
        viewModel.getActivity().addOnPropertyChangedCallback(new PropertyChangedCallback<ObservableActivity>() {
            @Override
            public void callback(ObservableActivity observable, int fieldId) {
                if (fieldId == BR.finish) {
                    setResult(observable.getFinish().get().getResultCode(), observable.getFinish().get().getIntent());
                    finish();
                } else if (fieldId == BR.startActivity) {
                    startActivity(observable.getStartActivity().get());
                } else if (fieldId == BR.startActivityForResult) {
                    startActivityForResult(observable.getStartActivityForResult().get().getIntent(), observable.getStartActivityForResult().get().getRequestCode());
                }
            }
        });
        viewModel.getToast().addOnPropertyChangedCallback(new PropertyChangedCallback<ObservableToast>() {
            @Override
            public void callback(ObservableToast observable, int fieldId) {
                Toast.makeText(getApplicationContext(), observable.getText(), observable.getDuration()).show();
            }
        });
        viewModel.getProgressDialog().addOnPropertyChangedCallback(new PropertyChangedCallback<ObservableProgressDialog>() {
            @Override
            public void callback(ObservableProgressDialog observable, int fieldId) {
                if (observable.isShow()) {
                    if (observable.getText() != null)
                        dialogViewModel.setMessage(observable.getText());
                    dialogViewModel.show();
                }else {
                    dialogViewModel.dismiss();
                }
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
