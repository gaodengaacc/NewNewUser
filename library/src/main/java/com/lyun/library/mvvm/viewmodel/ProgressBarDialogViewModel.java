package com.lyun.library.mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.View;

import com.lyun.widget.dialog.ProgressBarDialog;

/**
 * @author Gordon
 * @since 2017/1/16
 * do()
 */

public class ProgressBarDialogViewModel extends DialogViewModel {

    public final ObservableField<String> progressText = new ObservableField<>();
    public final ObservableInt progressTextVisibility = new ObservableInt();
    private LoadingCancelCallBack loadingCancel;

    public ProgressBarDialogViewModel(Context context, String text) {
        super(context);
        setMessage(text);
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
        if (message == null || TextUtils.isEmpty(message)) {
            progressTextVisibility.set(View.GONE);
        } else {
            progressTextVisibility.set(View.VISIBLE);
        }
    }

    public void dismiss() {
        super.dismiss();
        if (loadingCancel != null)
            loadingCancel.loadCancel();
        isShow.set(false);
    }

}
