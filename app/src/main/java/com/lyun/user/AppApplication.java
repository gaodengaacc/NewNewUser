package com.lyun.user;

import android.app.Activity;

import com.lyun.BaseApplication;
import com.lyun.user.api.API;
import com.lyun.utils.CrashUtil;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author 赵尉尉
 * @date 2016/12/20
 */

public class AppApplication extends BaseApplication {

    private static AppApplication instance;
    private List<Activity> mList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 初始化接口
        API.init("https://lyt.law-cloud.com.cn:8444");
        // createCrash();
    }

    //添加app崩溃日志，保存到手机上
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