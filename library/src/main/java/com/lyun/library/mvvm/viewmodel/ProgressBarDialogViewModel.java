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
    public final ObservableInt lineVisibility = new ObservableInt();
    public final ObservableInt textBottomVisibility = new ObservableInt();
    public final ObservableField<String> textBottomText = new ObservableField<>();
    private OnBottomClickCallBack onBottomClickCallBack;
    public ProgressBarDialogViewModel(Context context, String text) {
        super(context);
        setMessage(text);
        init();
        new ProgressBarDialog(getContext(), this);
    }

    private void init() {
        progressText.set(progressText.get() == null ? "加载中..." : progressText.get());
        textBottomVisibility.set(View.GONE);
        textBottomVisibility.set(View.GONE);
        lineVisibility.set(View.GONE);
        isOutSideCancel.set(true);
    }

    public ProgressBarDialogViewModel(Context context) {
        super(context);
        init();
        new ProgressBarDialog(getContext(), this);
    }

    public interface OnBottomClickCallBack {
        public void onClick(View view);
    }

    public void setOnBottomClickCallBack(OnBottomClickCallBack onBottomClickCallBack) {
        this.onBottomClickCallBack = onBottomClickCallBack;
    }

    public void setMessage(String message) {
        if (message == null || TextUtils.isEmpty(message)) {
            progressTextVisibility.set(View.GONE);
        } else {
            progressText.set(message);
            progressTextVisibility.set(View.VISIBLE);
        }
    }

    public void dismiss() {
        super.dismiss();
        isShow.set(false);
    }

    public void setBottomMessage(String message) {
        if (message == null || TextUtils.isEmpty(message)) {
            lineVisibility.set(View.GONE);
            textBottomVisibility.set(View.GONE);
        } else {
            textBottomText.set(message);
            lineVisibility.set(View.VISIBLE);
            textBottomVisibility.set(View.VISIBLE);
        }
    }

    public void onBottomClick(View view) {
        if (onBottomClickCallBack != null)
            onBottomClickCallBack.onClick(view);
        dismiss();
    }

    public void setOnOutSideCancel(Boolean cancel) {
            isOutSideCancel.set(cancel);
    }
}
