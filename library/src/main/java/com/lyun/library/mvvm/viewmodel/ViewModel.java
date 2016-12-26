package com.lyun.library.mvvm.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.lyun.BaseApplication;
import com.lyun.library.mvvm.model.Model;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public abstract class ViewModel extends BaseObservable {

    private Context mContext;

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

}
