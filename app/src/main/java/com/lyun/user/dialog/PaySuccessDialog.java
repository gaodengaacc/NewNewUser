package com.lyun.user.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import com.lyun.library.mvvm.view.dialog.MvvmDialog;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityPaySuccessBinding;
import com.lyun.user.viewmodel.PaySuccessDialogViewModel;
import com.lyun.utils.DisplayUtil;

/**
 * @author Gordon
 * @since 2017/10/26
 * do()
 */

public class PaySuccessDialog extends MvvmDialog<ActivityPaySuccessBinding, PaySuccessDialogViewModel> {


    public PaySuccessDialog(@NonNull Context context, PaySuccessDialogViewModel mDialogViewModel) {
        super(context, mDialogViewModel, R.layout.activity_pay_success, R.style.paydialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {//设置dialog显示位置
        setContentView(R.layout.activity_pay_success);
        //宽度全屏
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = DisplayUtil.getScreenMetrics(getContext()).x;
        layoutParams.height = DisplayUtil.getScreenMetrics(getContext()).y;
        getWindow().setAttributes(layoutParams);
        //点击dialog外部消失
        setCanceledOnTouchOutside(false);
        super.onCreate(savedInstanceState);
    }
}
