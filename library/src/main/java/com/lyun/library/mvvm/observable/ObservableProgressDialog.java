package com.lyun.library.mvvm.observable;

import android.databinding.BaseObservable;

/**
 * @author Gordon
 * @since 2017/1/9
 * do()
 */

public class ObservableProgressDialog extends BaseObservable {

    protected String text;
    protected boolean isShow;

    public void show() {
        isShow = true;
        notifyChange();
    }

    public void show(String text) {
        this.text = text;
        isShow = true;
        notifyChange();
    }

    public void dismiss() {
        isShow = false;
        notifyChange();
    }

    public boolean isShow() {
        return isShow;
    }

    public String getText() {
        return text;
    }

    public synchronized void addOnPropertyChangedCallback(PropertyChangedCallback<ObservableProgressDialog> callback) {
        super.addOnPropertyChangedCallback(callback);
    }
}
