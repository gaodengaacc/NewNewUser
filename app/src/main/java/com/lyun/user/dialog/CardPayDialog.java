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
import com.lyun.user.databinding.DialogCardPayBinding;
import com.lyun.user.viewmodel.CardPayDialogViewModel;
import com.lyun.utils.DisplayUtil;

/**
 * @author Gordon
 * @since 2017/8/1
 * do()
 */

public class CardPayDialog extends MvvmDialog<DialogCardPayBinding,CardPayDialogViewModel>{
    public CardPayDialog(@NonNull Context context, CardPayDialogViewModel mDialogViewModel) {
        super(context, mDialogViewModel, R.layout.dialog_card_pay, R.style.dialog);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);//设置dialog显示位置
        setContentView(R.layout.dialog_card_pay);
        //宽度全屏
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = DisplayUtil.getScreenMetrics(getContext()).x;
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
