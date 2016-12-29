package com.lyun.library.mvvm.observable;

import android.databinding.Observable;

/**
 * Created by ZHAOWEIWEI on 2016/12/28.
 */

public abstract class PropertyChangedCallback<T extends Observable> extends Observable.OnPropertyChangedCallback {

    @Override
    public void onPropertyChanged(Observable observable, int fieldId) {
        callback((T) observable, fieldId);
    }


    public abstract void callback(T observable, int fieldId);

}
