package com.lyun.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.lyun.library.R;
import com.lyun.library.databinding.IncludeDialogProgressbarBinding;

/**
 * 加载中dialog
 */
public class ProgressBarDialog extends Dialog {
    private Context context;
    private String progressText;
    private LoadingCancelCallBack loadingCancel;
    private IncludeDialogProgressbarBinding viewBinding;
    public ProgressBarDialog(Context context) {
        super(context, R.style.dialog_theme);
        this.context = context;
    }

    public ProgressBarDialog(Context context, String progressText) {
        super(context, R.style.dialog_theme);
        this.context = context;
        this.progressText = progressText;
    }


    public void setLoadingCancel(LoadingCancelCallBack loadingCancel) {
        this.loadingCancel = loadingCancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         viewBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.include_dialog_progressbar,null,false);
         this.setContentView(viewBinding.getRoot());
         viewBinding.setText(progressText ==null ? "加载中...":progressText);
    }

    public void setMessage(String message) {
        viewBinding.setText(message);
    }

    @Override
    public void show() {
        if (!isShowing() && context != null) {
            super.show();
        }
    }


    @Override
    public void dismiss() {
        if (loadingCancel != null && isShowing()) {
            loadingCancel.loadCancel();
        }
        super.dismiss();
    }

    public interface LoadingCancelCallBack {
        public void loadCancel();
    }


}
