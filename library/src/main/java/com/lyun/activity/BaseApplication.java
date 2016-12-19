package com.lyun.activity;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * @author Gordon
 * @since 2016/12/15
 * do(基类Application)
 */

public class BaseApplication extends MultiDexApplication {
    private static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }
    public static BaseApplication getInstance() {
        return instance;
    }
    private void init() {
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
