package com.lyun.library.mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.lyun.widget.dialog.ProgressBarDialog;

/**
 * @author Gordon
 * @since 2017/1/16
 * do()
 */

public class ProgressBarDialogViewModel extends ViewModel {

    public final ObservableField<String> progressText = new ObservableField<>();
    public final ObservableBoolean isShow = new ObservableBoolean();
    private LoadingCancelCallBack loadingCancel;

    public ProgressBarDialogViewModel(Context context, String text) {
        super(context);
        progressText.set(text);
        init();
        ProgressBarDialog dialog = new ProgressBarDialog(getContext(), this);
    }

    private void init() {
        progressText.set(progressText.get() == null ? "加载中..." : progressText.get());
    }

    public ProgressBarDialogViewModel(Context context) {
        super(context);
        init();
        ProgressBarDialog dialog = new ProgressBarDialog(getContext(), this);
    }

    public interface LoadingCancelCallBack {
        public void loadCancel();
    }

    public void setLoadingCancel(LoadingCancelCallBack loadingCancel) {
        this.loadingCancel = loadingCancel;
    }

    public void setMessage(String message) {
        progressText.set(message);
    }

    public void show() {
        if (getContext() != null) {
            isShow.set(true);
        }
    }
    public void dismiss(){
       if(loadingCancel!=null)
           loadingCancel.loadCancel();
        isShow.set(false);
    }

}
