package com.lyun.library.mvvm.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;

import com.lyun.library.mvvm.observable.ObservableActivity;
import com.lyun.library.mvvm.observable.ObservableToast;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public abstract class ViewModel extends BaseObservable {

    private Context mContext;

    protected final ObservableActivity mObservableActivity = new ObservableActivity();
    protected final ObservableToast mObservableToast = new ObservableToast();

    private ViewModel() {
        super();
    }

    public ViewModel(Context context) {
        this();
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public final ObservableBoolean activityFinish = new ObservableBoolean(false);

    public void finishActivity() {
        activityFinish.set(true);
    }

    public ObservableActivity getActivity() {
        return mObservableActivity;
    }

    public ObservableToast getToast() {
        return mObservableToast;
    }
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
    };


}
