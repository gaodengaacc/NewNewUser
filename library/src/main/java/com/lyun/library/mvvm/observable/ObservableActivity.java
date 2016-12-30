package com.lyun.library.mvvm.observable;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.lyun.library.BR;

/**
 * Created by ZHAOWEIWEI on 2016/12/28.
 */

public class ObservableActivity extends BaseObservable {

    private ObservableField<Result> finish = new ObservableField<>();
    private ObservableField<Intent> startActivity = new ObservableField();

    public void finish() {
        finish.set(Result.defult());
        notifyPropertyChanged(BR.finish);
    }

    public void startActivity(Intent intent) {
        startActivity.set(intent);
        notifyPropertyChanged(BR.startActivity);
    }

    @Bindable
    public ObservableField<Result> getFinish() {
        return finish;
    }

    @Bindable
    public ObservableField<Intent> getStartActivity() {
        return startActivity;
    }

    public synchronized void addOnPropertyChangedCallback(PropertyChangedCallback<ObservableActivity> callback) {
        super.addOnPropertyChangedCallback(callback);
    }

    public static class Result {

        private int resultCode;
        private Intent intent;

        public Result(int resultCode, Intent intent) {
            this.resultCode = resultCode;
            this.intent = intent;
        }

        public Result(int resultCode) {
            this.resultCode = resultCode;
        }

        public int getResultCode() {
            return resultCode;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }

        public Intent getIntent() {
            return intent;
        }

        public void setIntent(Intent intent) {
            this.intent = intent;
        }

        public static Result defult() {
            return new Result(Activity.RESULT_OK);
        }
    }
}
