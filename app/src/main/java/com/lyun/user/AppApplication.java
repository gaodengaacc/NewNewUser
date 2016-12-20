package com.lyun.user;

import com.lyun.BaseApplication;
import com.lyun.user.api.API;

/**
 *
 * @author 赵尉尉
 * @date 2016/12/20
 */

public class AppApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        API.init("https://lyt.law-cloud.com.cn:8444");
    }

}
