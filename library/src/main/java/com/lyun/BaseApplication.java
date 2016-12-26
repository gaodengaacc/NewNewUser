package com.lyun;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.lyun.utils.CrashUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;

/**
 * @author Gordon
 * @since 2016/12/15
 * do(基类Application)
 */
public abstract class BaseApplication extends Application {

    protected static BaseApplication instance;
    protected static AppFileDirs mAppFileDirs;

    // LeakCanary
    protected RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 文件系统
        initFileSystem();
        // 内存泄漏检测
        initLeakCanary();
        // 崩溃日志
        initCrashLog();
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 初始化内存泄漏检测
     */
    protected void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        mRefWatcher = LeakCanary.install(this);
    }

    /**
     * 初始化文件路径
     */
    private void initFileSystem() {
        File externPath = Environment.getExternalStorageDirectory();
        mAppFileDirs = AppFileDirs.instance().create(externPath.getAbsolutePath() + "/" + getStorageHomeDirName());
    }

    /**
     * 添加app崩溃日志，保存到手机上
     */
    private void initCrashLog() {
        CrashUtil.getInstance().init(this, getAppFileDirs().log().getAbsolutePath());
    }

    protected abstract String getStorageHomeDirName();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static RefWatcher getRefWatcher() {
        return instance.mRefWatcher;
    }

    public static AppFileDirs getAppFileDirs() {
        return mAppFileDirs;
    }

}
