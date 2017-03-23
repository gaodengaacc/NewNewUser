package com.lyun.library.mvvm.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import com.lyun.library.BR;
import com.lyun.library.mvvm.viewmodel.DialogViewModel;
import com.lyun.library.mvvm.viewmodel.watchdog.IDialogViewModelCallbacks;

/**
 * Created by 郑成裕 on 2017/1/11.
 */

public abstract class MvvmDialog<VDB extends ViewDataBinding, VM extends DialogViewModel> extends Dialog implements IDialogViewModelCallbacks {
    protected VM mDialogViewModel;
    private int layoutId;

    public MvvmDialog(@NonNull Context context, VM mDialogViewModel, int layoutId, int themeId) {
        super(context, themeId);
        this.layoutId = layoutId;
        this.mDialogViewModel = mDialogViewModel;
        mDialogViewModel.setPropertyChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VDB viewBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), layoutId, null, false);
        viewBinding.setVariable(BR.mvvm, mDialogViewModel);
        this.setContentView(viewBinding.getRoot());
    }

    @Override
    public void isShow(ObservableBoolean observableField, int fieldId) {
        if (!isShowing())
            show();
    }

    @Override
    public void isDismiss(ObservableBoolean observableField, int fieldId) {
        if (isShowing())
            dismiss();
    }

    @Override
    public void isOutSideCancel(ObservableBoolean observableField, int fieldId) {
        setCancelable(observableField.get());
        setCanceledOnTouchOutside(observableField.get());
    }
}
