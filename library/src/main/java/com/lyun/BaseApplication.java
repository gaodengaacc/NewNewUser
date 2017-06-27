package com.lyun;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.lyun.utils.CrashUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/15
 * do(基类Application)
 */
public abstract class BaseApplication extends Application {

    protected static BaseApplication instance;
    protected static AppFileDirs mAppFileDirs;

    protected List<? extends ApplicationDelegate> mDelegates;

    // LeakCanary
    protected RefWatcher mRefWatcher;

    @Override
    public void onCreate() {

        mDelegates = getDelegates();

        super.onCreate();
        instance = this;
        // 文件系统
        initFileSystem();
        // 内存泄漏检测
        initLeakCanary();
        // 崩溃日志
        initCrashLog();

        for (ApplicationDelegate delegate : mDelegates) {
            delegate.onCreate();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        for (ApplicationDelegate delegate : mDelegates) {
            delegate.onTerminate();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        for (ApplicationDelegate delegate : mDelegates) {
            delegate.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        for (ApplicationDelegate delegate : mDelegates) {
            delegate.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        for (ApplicationDelegate delegate : mDelegates) {
            delegate.onTrimMemory(level);
        }
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

    @NonNull
    protected List<ApplicationDelegate> getDelegates() {
        return new ArrayList<>();
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

    public boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = getProcessName(this);
        return packageName.equals(processName);
    }

    /**
     * 获取当前进程名
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;

                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
