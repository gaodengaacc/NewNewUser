package com.lyun.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;

import com.lyun.library.R;
import com.lyun.library.databinding.DialogProgressBinding;
import com.lyun.library.mvvm.view.dialog.MvvmDialog;
import com.lyun.library.mvvm.viewmodel.ProgressDialogViewModel;

/**
 * 进度对话框
 *
 * @author 赵尉尉
 * @since 2015/7/3 18:04
 */
public class ProgressDialog extends MvvmDialog<DialogProgressBinding,ProgressDialogViewModel> {
    public ProgressDialog(Context context,ProgressDialogViewModel viewModel) {
        super(context, viewModel, R.layout.dialog_progress,R.style.dialog);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(false);
        super.onCreate(savedInstanceState);
    }
}