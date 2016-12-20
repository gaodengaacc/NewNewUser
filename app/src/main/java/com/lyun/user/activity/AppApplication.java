package com.lyun.user.activity;

import android.app.Activity;

import com.lyun.activity.BaseApplication;
import com.lyun.utils.CrashUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/19
 * do(Application)
 */

public class AppApplication extends BaseApplication {
    private static AppApplication instance;
    private List<Activity> mList = new LinkedList<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        createCrash();
    }
   //添加app奔溃日志，保存到手机上
    private void createCrash() {
        CrashUtil crashUtil = CrashUtil.getInstance();
        crashUtil.init(this);
    }

    public static AppApplication getInstance() {
        return instance;
    }
    //添加创建的Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    //关闭每一个list内的activity
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
