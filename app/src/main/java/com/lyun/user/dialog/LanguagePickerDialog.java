package com.lyun.user.dialog;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lyun.library.mvvm.view.dialog.MvvmDialog;
import com.lyun.user.R;
import com.lyun.user.adapter.LanguageTextAdapter;
import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.user.databinding.DialogLanguagePickerBinding;
import com.lyun.user.viewmodel.LanguagePickerDialogViewModel;
import com.lyun.user.viewmodel.watchdog.ILanguagePickerDialogViewModelCallbacks;
import com.lyun.utils.DisplayUtil;

import java.util.List;

import kankan.wheel.widget.WheelView;

/**
 * 目标语言选择Dialog
 * Created by 郑成裕 on 2017/1/6.
 */

public class LanguagePickerDialog extends MvvmDialog<DialogLanguagePickerBinding, LanguagePickerDialogViewModel>
        implements ILanguagePickerDialogViewModelCallbacks {

    public OnLanguagePickListener onLanguagePickListener;

    public LanguagePickerDialog(@NonNull Context context, LanguagePickerDialogViewModel mDialogViewModel) {
        super(context, mDialogViewModel, R.layout.dialog_language_picker, R.style.dialog);
        mDialogViewModel.setPropertyChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);//设置dialog显示位置
        setContentView(R.layout.dialog_language_picker);
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

    //设置选择时字体大小
    private static int maxTextSize = 16;
    private static int minTextSize = 14;

    @BindingAdapter("languageDatas")
    public static void setLanguageDatas(final WheelView wheelView, List<FindLanguageResponse> datas) {
        LanguageTextAdapter adapter = new LanguageTextAdapter(wheelView.getContext(), datas, wheelView.getCurrentItem(), maxTextSize, minTextSize);
        wheelView.setViewAdapter(adapter);
    }

    @Override
    public void onLanguagePicked(ObservableField<FindLanguageResponse> observableField, int fieldId) {
        if (onLanguagePickListener != null) {
            onLanguagePickListener.onLanguagePicked(observableField.get());
        }
        dismiss();
    }

    public void setOnLanguagePickListener(OnLanguagePickListener onLanguagePickListener) {
        this.onLanguagePickListener = onLanguagePickListener;
    }

    public interface OnLanguagePickListener {
        void onLanguagePicked(FindLanguageResponse language);
    }
}
