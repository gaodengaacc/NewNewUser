package com.lyun.user.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lyun.library.mvvm.view.dialog.MvvmDialog;
import com.lyun.user.R;
import com.lyun.user.databinding.DialogLanguagePickerBinding;
import com.lyun.user.viewmodel.LanguagePickerDialogViewModel;

/**
 * 目标语言选择Dialog
 * Created by 郑成裕 on 2017/1/6.
 */

public class LanguagePickerDialog extends MvvmDialog<DialogLanguagePickerBinding, LanguagePickerDialogViewModel> {
    private Context context;

    public LanguagePickerDialog(@NonNull Context context, LanguagePickerDialogViewModel mDialogViewModel) {
        super(context, mDialogViewModel, R.layout.dialog_language_picker, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);//设置dialog显示位置
        setContentView(R.layout.dialog_language_picker);
        //宽度全屏
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = display.getWidth();
        getWindow().setAttributes(layoutParams);
        //点击dialog外部消失
        setCanceledOnTouchOutside(true);
        super.onCreate(savedInstanceState);
    }
}
