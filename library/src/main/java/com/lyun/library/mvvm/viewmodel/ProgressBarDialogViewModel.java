package com.lyun.library.mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import com.lyun.widget.dialog.ProgressBarDialog;

/**
 * @author Gordon
 * @since 2017/1/16
 * do()
 */

public class ProgressBarDialogViewModel extends DialogViewModel {

    public final ObservableField<String> progressText = new ObservableField<>();
    private LoadingCancelCallBack loadingCancel;
    public ProgressBarDialogViewModel(Context context, String text) {
        super(context);
        progressText.set(text);
        init();
        new ProgressBarDialog(getContext(), this);
    }

    private void init() {
        progressText.set(progressText.get() == null ? "加载中..." : progressText.get());
    }

    public ProgressBarDialogViewModel(Context context) {
        super(context);
        init();
        new ProgressBarDialog(getContext(), this);
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
    public void dismiss(){
        super.dismiss();
       if(loadingCancel!=null)
           loadingCancel.loadCancel();
        isShow.set(false);
    }

}
