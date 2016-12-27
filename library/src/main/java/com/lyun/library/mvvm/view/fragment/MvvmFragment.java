package com.lyun.library.mvvm.view.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lyun.fragment.BaseFragment;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.library.BR;

/**
 * @author Gordon
 * @since 2016/12/26
 * do(基类Fragment)
 */

public abstract class MvvmFragment<VDB extends ViewDataBinding, VM extends ViewModel> extends BaseFragment {

    private VDB mFragmentViewDataBinding;
    protected VM mFragmentViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentViewDataBinding = DataBindingUtil.inflate(inflater, getContentLayoutId(), container, false);
        mFragmentViewModel = createViewModel();
        registerViewModel(mFragmentViewModel);
        mFragmentViewDataBinding.setVariable(BR.viewModel,mFragmentViewModel);
        return mFragmentViewDataBinding.getRoot();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyViewDataBinding(mFragmentViewDataBinding);
    }

    protected void destroyViewDataBinding(ViewDataBinding viewDataBinding) {
        viewDataBinding.unbind();
        viewDataBinding.executePendingBindings();
    }

    protected <T extends ViewModel> T registerViewModel(T viewModel) {
        viewModel.activityFinish.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                mFragmentViewModel.activityFinish.set(false);
                getActivity().finish();
            }
        });
        return viewModel;
    }

    @NonNull
    protected abstract VM createViewModel();

    @LayoutRes
    protected abstract int getContentLayoutId();

    protected VM getFragmentViewModel() {
        return mFragmentViewModel;
    }

    protected VDB getFragmentViewDataBinding() {
        return mFragmentViewDataBinding;
    }
}
