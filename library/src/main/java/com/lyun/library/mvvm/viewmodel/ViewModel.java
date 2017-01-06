package com.lyun.library.mvvm.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;

import com.lyun.library.mvvm.observable.ObservableActivity;
import com.lyun.library.mvvm.observable.ObservableToast;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public abstract class ViewModel extends BaseObservable {

    @Deprecated
    private Context mContext;

    protected final ObservableActivity mObservableActivity = new ObservableActivity();
    protected final ObservableToast mObservableToast = new ObservableToast();

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroy() {
    }

    public void onDestroyView() {

    }
}
