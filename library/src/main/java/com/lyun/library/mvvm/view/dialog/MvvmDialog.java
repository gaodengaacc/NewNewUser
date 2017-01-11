package com.lyun.library.mvvm.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * Created by 郑成裕 on 2017/1/11.
 */

public abstract class MvvmDialog<VDB extends ViewDataBinding, VM extends ViewModel> extends Dialog {
    private VDB mDialogViewDataBinding;
    protected VM mDialogViewModel;

    public MvvmDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
