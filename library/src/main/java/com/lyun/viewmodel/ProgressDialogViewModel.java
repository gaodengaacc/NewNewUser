package com.lyun.viewmodel;

import android.content.Context;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.view.View;

import com.lyun.library.BR;
import com.lyun.widget.dialog.ProgressBarDialog;
import com.lyun.widget.dialog.SimpleMessageDialog;

/**
 * @author Gordon
 * @since 2016/12/22
 * do(上传数据的Dialog ViewModel)
 */

public class ProgressDialogViewModel extends BaseViewModel {
    public final ObservableField<String> text = new ObservableField<>();
    public final ObservableField<Integer> maxProgress = new ObservableField<>();
    public final ObservableField<Integer> progress = new ObservableField<>();
    public ProgressDialogViewModel(Context context) {
        super(context);
        text.set("正在上传数据...");
        maxProgress.set(100);
        progress.set(0);
    }
}
