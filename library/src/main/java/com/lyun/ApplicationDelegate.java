package com.lyun;

import android.app.Application;
import android.content.res.Configuration;
import android.support.annotation.CallSuper;

/**
 * Created by ZHAOWEIWEI on 2017/1/18.
 */

public abstract class ApplicationDelegate<T extends Application> {

    private T mApplication;

    public ApplicationDelegate(T application) {
        this.mApplication = application;
    }

    public abstract void onCreate();

    public abstract void onTerminate();

    public abstract void onConfigurationChanged(Configuration newConfig);

    public abstract void onLowMemory();

    public abstract void onTrimMemory(int level);

    public T getApplication() {
        return mApplication;
    }

    public String getString(int resId) {
        return getApplication().getString(resId);
    }
}
