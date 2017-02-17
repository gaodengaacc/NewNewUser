package com.lyun.user;

import com.lyun.ApplicationDelegate;
import com.lyun.BaseApplication;
import com.lyun.http.HeaderInterceptor;
import com.lyun.http.HttpsSocketFactoryBuilder;
import com.lyun.http.LogInterceptor;
import com.lyun.user.api.API;
import com.lyun.user.im.NimApplicationDelegate;
import com.lyun.utils.L;

import java.util.List;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Headers;

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
            API.init(Constants.API_BASE_URL, getSSLSocketFactory(), mHeaderInterceptor, new LogInterceptor());
        } else {
            API.init(Constants.API_BASE_URL, getSSLSocketFactory(), mHeaderInterceptor);
        }
    }

    private HeaderInterceptor mHeaderInterceptor = new HeaderInterceptor() {
        @Override
        public Headers getHeaders() {
            return new Headers.Builder()
                    .add("Authorization", Account.preference().getToken() + "")
                    .build();
        }
    };

    private SSLSocketFactory getSSLSocketFactory() {
        return new HttpsSocketFactoryBuilder()
                .trust(getResources().openRawResource(R.raw.trust), "lyt2016")
                // .client(getResources().openRawResource(R.raw.client), "password")
                .build();
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