package com.lyun.widget.dialog;
import android.content.Context;
import android.os.Bundle;
import com.lyun.library.R;
import com.lyun.library.databinding.IncludeDialogProgressbarBinding;
import com.lyun.library.mvvm.view.dialog.MvvmDialog;
import com.lyun.library.mvvm.viewmodel.ProgressBarDialogViewModel;

/**
 * 加载中dialog
 */
public class ProgressBarDialog extends MvvmDialog<IncludeDialogProgressbarBinding,ProgressBarDialogViewModel> {
    public ProgressBarDialog(Context context, ProgressBarDialogViewModel viewModel) {
        super(context,viewModel, R.layout.include_dialog_progressbar,R.style.dialog_theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
