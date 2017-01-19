package com.lyun.user;

import com.lyun.ApplicationDelegate;
import com.lyun.BaseApplication;
import com.lyun.http.LogInterceptor;
import com.lyun.user.api.API;
import com.lyun.user.im.NimApplicationDelegate;
import com.lyun.utils.L;

import java.util.List;

/**
 * @author 赵尉尉
 * @date 2016/12/20
 */

public class AppApplication extends BaseApplication {
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
    protected List<ApplicationDelegate> getDelegates() {
        List<ApplicationDelegate> delegates = super.getDelegates();
        delegates.add(new NimApplicationDelegate(this));
        return delegates;
    }

    @Override
    protected String getStorageHomeDirName() {
        return "law-cloud/user";
    }
}