package com.lyun.library.mvvm.view.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lyun.fragment.BaseFragment;
import com.lyun.library.BR;
import com.lyun.library.mvvm.observable.ObservableActivity;
import com.lyun.library.mvvm.observable.ObservableProgressDialog;
import com.lyun.library.mvvm.observable.ObservableToast;
import com.lyun.library.mvvm.observable.PropertyChangedCallback;
import com.lyun.library.mvvm.viewmodel.ProgressBarDialogViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * @author Gordon
 * @since 2016/12/26
 * do(基类Fragment)
 */

public abstract class MvvmFragment<VDB extends ViewDataBinding, VM extends ViewModel> extends BaseFragment {

    private VDB mFragmentViewDataBinding;
    protected VM mFragmentViewModel;
    protected ProgressBarDialogViewModel dialogViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        dialogViewModel = new ProgressBarDialogViewModel(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentViewDataBinding = DataBindingUtil.inflate(inflater, getContentLayoutId(), container, false);
        mFragmentViewModel = createViewModel();
        registerViewModel(mFragmentViewModel);
        mFragmentViewDataBinding.setVariable(BR.mvvm, mFragmentViewModel);
        return mFragmentViewDataBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentViewModel.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mFragmentViewModel.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFragmentViewModel.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFragmentViewModel.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyViewDataBinding(mFragmentViewDataBinding);
        if (dialogViewModel != null) {
            dialogViewModel = null;
        }
    }

    protected void destroyViewDataBinding(ViewDataBinding viewDataBinding) {
        viewDataBinding.unbind();
        viewDataBinding.executePendingBindings();
    }

    @Deprecated
    protected <T extends ViewModel> T registerViewModel(final T viewModel) {
        viewModel.getActivity().addOnPropertyChangedCallback(new PropertyChangedCallback<ObservableActivity>() {
            @Override
            public void callback(ObservableActivity observable, int fieldId) {
                if (fieldId == BR.finish) {
                    getActivity().setResult(observable.getFinish().get().getResultCode(), observable.getFinish().get().getIntent());
                    getActivity().finish();
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
                Toast.makeText(getContext(), observable.getText(), observable.getDuration()).show();
            }
        });
        viewModel.getProgressDialog().addOnPropertyChangedCallback(new PropertyChangedCallback<ObservableProgressDialog>() {
            @Override
            public void callback(ObservableProgressDialog observable, int fieldId) {
                if (observable.isShow()) {
                    if (observable.getText() != null)
                        dialogViewModel.setMessage(observable.getText());
                    dialogViewModel.show();
                } else {
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

    protected VM getFragmentViewModel() {
        return mFragmentViewModel;
    }

    protected VDB getFragmentViewDataBinding() {
        return mFragmentViewDataBinding;
    }
}
