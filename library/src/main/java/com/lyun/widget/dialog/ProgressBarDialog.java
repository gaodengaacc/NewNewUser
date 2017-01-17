package com.lyun.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.lyun.library.R;
import com.lyun.library.databinding.IncludeDialogProgressbarBinding;
import com.lyun.library.mvvm.viewmodel.ProgressBarDialogViewModel;

/**
 * 加载中dialog
 */
public class ProgressBarDialog extends Dialog {
    private Context context;
    private ProgressBarDialogViewModel viewModel;

    public ProgressBarDialog(Context context) {
        super(context, R.style.dialog_theme);
        this.context = context;
    }

    public ProgressBarDialog(Context context, ProgressBarDialogViewModel viewModel) {
        super(context, R.style.dialog_theme);
        this.context = context;
        this.viewModel = viewModel;
        registerViewModel(viewModel);
    }

    private void registerViewModel(final ProgressBarDialogViewModel viewModel) {
        viewModel.isShow.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (viewModel.isShow.get()) {
                    show();
                } else {
                    dismiss();
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IncludeDialogProgressbarBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.include_dialog_progressbar, null, false);
        if (viewModel != null)
            viewBinding.setMvvm(viewModel);
        this.setContentView(viewBinding.getRoot());
    }


    @Override
    public void show() {
        if (!isShowing()) {
            super.show();
        }
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }


}
