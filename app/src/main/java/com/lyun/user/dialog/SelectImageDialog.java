package com.lyun.user.dialog;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lyun.library.mvvm.view.dialog.MvvmDialog;
import com.lyun.user.R;
import com.lyun.user.databinding.DialogSelectImageBinding;
import com.lyun.user.viewmodel.SelectImageDialogViewModel;

/**
 * @author Gordon
 * @since 2017/8/3
 * do()
 */

public class SelectImageDialog extends MvvmDialog<DialogSelectImageBinding,SelectImageDialogViewModel> {
    public SelectImageDialog(@NonNull Context context, SelectImageDialogViewModel mDialogViewModel) {
        super(context, mDialogViewModel, R.layout.dialog_select_image, R.style.dialog);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);//设置dialog显示位置
        setContentView(R.layout.dialog_select_image);
        //宽度全屏
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.y = 40;
        getWindow().setAttributes(layoutParams);
        //点击dialog外部消失
        setCanceledOnTouchOutside(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void isDismiss(ObservableBoolean observableField, int fieldId) {
        super.isDismiss(observableField, fieldId);
    }
}
