package com.lyun.library.mvvm.observable;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.lyun.library.BR;

/**
 * Created by ZHAOWEIWEI on 2016/12/28.
 */

public class ObservableActivity extends BaseObservable {

    private ObservableBoolean finish = new ObservableBoolean();
    private ObservableField<Intent> startActivity = new ObservableField();
    private ObservableField<Intent> startActivityForResult = new ObservableField<>();

    public void finish() {
        finish.set(!finish.get());
        notifyPropertyChanged(BR.finish);
    }

    public void startActivity(Intent intent) {
        startActivity.set(intent);
        notifyPropertyChanged(BR.startActivity);
    }

//    public void startActivityForResult(Intent intent, int requestCode) {
//
//    }

    @Bindable
    public ObservableBoolean getFinish() {
        return finish;
    }

    @Bindable
    public ObservableField<Intent> getStartActivity() {
        return startActivity;
    }

    public synchronized void addOnPropertyChangedCallback(PropertyChangedCallback<ObservableActivity> callback) {
        super.addOnPropertyChangedCallback(callback);
    }
}
