package com.lyun.library.mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.lyun.widget.dialog.ProgressDialog;

/**
 * @author Gordon
 * @since 2016/12/22
 * do(上传数据的Dialog ViewModel)
 */
public class ProgressDialogViewModel extends DialogViewModel {
    public final ObservableField<String> text = new ObservableField<>();
    public final ObservableInt maxProgress = new ObservableInt();
    public final ObservableInt progress = new ObservableInt();
    public ProgressDialogViewModel(Context context) {
        super(context);
        text.set("正在上传数据...");
        maxProgress.set(100);
        progress.set(0);
        new ProgressDialog(context,this);
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress.set(maxProgress);
    }

    public void setProgress(int progress) {
        this.progress.set(progress);
    }

    public void setText(String text) {
        this.text.set(text);
    }
}

