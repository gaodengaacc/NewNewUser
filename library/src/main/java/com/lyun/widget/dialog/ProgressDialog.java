package com.lyun.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;

import com.lyun.library.R;
import com.lyun.library.databinding.DialogProgressBinding;
import com.lyun.library.mvvm.viewmodel.ProgressDialogViewModel;

/**
 * 进度对话框
 *
 * @author 赵尉尉
 * @since 2015/7/3 18:04
 */
public class ProgressDialog extends Dialog {

    private Context context;
    private ProgressDialogViewModel viewModel;

    public ProgressDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        DialogProgressBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_progress, null, false);
        viewModel = new ProgressDialogViewModel();
        viewBinding.setViewModel(viewModel);
        setContentView(viewBinding.getRoot());
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(false);
    }

    public void setMaxProgress(int maxProgress) {
        viewModel.maxProgress.set(maxProgress);
    }

    public void setProgress(int progress) {
        viewModel.progress.set(progress);
    }

    public void setText(String text) {
        viewModel.text.set(text);
    }
}
