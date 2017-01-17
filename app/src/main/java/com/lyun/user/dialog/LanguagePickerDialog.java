package com.lyun.user.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.lyun.user.R;
import com.lyun.user.databinding.DialogLanguagePickerBinding;
import com.lyun.user.viewmodel.LanguagePickerDialogViewModel;

/**
 * 目标语言选择Dialog
 * Created by 郑成裕 on 2017/1/6.
 */

public class LanguagePickerDialog extends Dialog {
    private Context context;
    LanguagePickerDialogViewModel viewModel;

    public LanguagePickerDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    public LanguagePickerDialog(Context context, LanguagePickerDialogViewModel viewModel) {
        super(context, R.style.dialog);
        this.context = context;
        this.viewModel = viewModel;
        registerViewModel(viewModel);
    }

    private void registerViewModel(LanguagePickerDialogViewModel viewModel) {
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);//设置dialog显示位置
//        window.setWindowAnimations(R.style.bottom_menu_animation);
        LayoutInflater inflater = LayoutInflater.from(context);
        DialogLanguagePickerBinding viewBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_language_picker, null, false);
        viewBinding.setMvvm(viewModel);
        // 设置LanguagePickerDialog的View
        this.setContentView(viewBinding.getRoot());
        //宽度全屏
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = display.getWidth();
        getWindow().setAttributes(layoutParams);
        //点击dialog外部消失
        setCanceledOnTouchOutside(true);

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
