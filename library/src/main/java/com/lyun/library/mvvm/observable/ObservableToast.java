package com.lyun.library.mvvm.observable;

import android.databinding.BaseObservable;
import android.support.annotation.IntDef;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ObservableToast extends BaseObservable {

    private CharSequence mText;
    private int mDuration;

    @IntDef({Toast.LENGTH_SHORT, Toast.LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    public void show(CharSequence text, @Duration int duration) {
        mText = text;
        mDuration = duration;
        notifyChange();
    }

    public void show(CharSequence text) {
        mText = text;
        mDuration = Toast.LENGTH_SHORT;
        notifyChange();
    }

    public CharSequence getText() {
        return mText;
    }

    public void setText(CharSequence text) {
        mText = text;
    }

    @Duration
    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public synchronized void addOnPropertyChangedCallback(PropertyChangedCallback<ObservableToast> callback) {
        super.addOnPropertyChangedCallback(callback);
    }

}
