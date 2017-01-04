package com.lyun.user;

import android.app.Activity;

import com.lyun.BaseApplication;
import com.lyun.http.LogInterceptor;
import com.lyun.user.api.API;
import com.lyun.utils.CrashUtil;
import com.lyun.utils.L;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 赵尉尉
 * @date 2016/12/20
 */

public class AppApplication extends BaseApplication {

    private List<Activity> mList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        // 显示log
        L.display(BuildConfig.DEBUG);
        // 初始化接口
        if (BuildConfig.DEBUG) {
            API.init(Constants.API_BASE_URL, new LogInterceptor());
        } else {
            API.init(Constants.API_BASE_URL);
        }
    }

    @Override
    protected String getStorageHomeDirName() {
        return "law-cloud/user";
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