package com.lyun.library.mvvm.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.lyun.library.mvvm.observable.ObservableActivity;
import com.lyun.library.mvvm.observable.ObservableProgressDialog;
import com.lyun.library.mvvm.observable.ObservableToast;

import net.funol.databinding.watchdog.Watchdog;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public abstract class ViewModel extends BaseObservable {

    @Deprecated
    private Context mContext;

    protected final ObservableActivity mObservableActivity = new ObservableActivity();
    protected final ObservableToast mObservableToast = new ObservableToast();
    protected final ObservableProgressDialog mObservableProgressDialog = new ObservableProgressDialog();

    public ViewModel() {
    }

    @Deprecated
    public ViewModel(Context context) {
        this.mContext = context;
    }

    @Deprecated
    public Context getContext() {
        return mContext;
    }

    public ObservableActivity getActivity() {
        return mObservableActivity;
    }

    public ObservableToast getToast() {
        return mObservableToast;
    }

    public <T extends ViewModel> T setPropertyChangeListener(Object beNotified) {
        Watchdog.newBuilder()
                .watch(this)
                .notify(beNotified)
                .build();
        return (T) this;
    }

    public ObservableProgressDialog getProgressDialog() {
        return mObservableProgressDialog;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroy() {
        mContext = null;
    }

    public void onDestroyView() {

    }

}
