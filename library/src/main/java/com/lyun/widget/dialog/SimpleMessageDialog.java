package com.lyun.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.lyun.library.R;
import com.lyun.library.databinding.DialogSimpleMessageBinding;
import com.lyun.library.mvvm.view.dialog.MvvmDialog;
import com.lyun.library.mvvm.viewmodel.SimpleDialogViewModel;

/**
 * @author 赵尉尉
 * @version 创建时间：2014年5月27日 上午10:12:47
 */

public class SimpleMessageDialog extends MvvmDialog<DialogSimpleMessageBinding, SimpleDialogViewModel> {

    public SimpleMessageDialog(Context context, SimpleDialogViewModel viewModel) {
        super(context, viewModel, R.layout.dialog_simple_message, R.style.dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
}
