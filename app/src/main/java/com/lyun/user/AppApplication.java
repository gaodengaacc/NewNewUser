package com.lyun.user;

import com.lyun.ApplicationDelegate;
import com.lyun.BaseApplication;
import com.lyun.http.AuthorizationInterceptor;
import com.lyun.http.HttpsSocketFactoryBuilder;
import com.lyun.http.LogInterceptor;
import com.lyun.user.activity.LoginActivity;
import com.lyun.user.api.API;
import com.lyun.user.im.NimApplicationDelegate;
import com.lyun.utils.L;
import com.smarttop.library.db.AssetsDatabaseManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.util.List;

import javax.net.ssl.SSLSocketFactory;

/**
 * @author 赵尉尉
 * @date 2016/12/20
 */

public class AppApplication extends BaseApplication {
    private IWXAPI msgApi;
    private Tencent mTencent;
    @Override
    public void onCreate() {
        super.onCreate();
        // 显示log
        L.display(BuildConfig.DEBUG);
        // 初始化接口
        if (BuildConfig.DEBUG) {
            API.init(Constants.API_BASE_URL, getSSLSocketFactory(),
                    mAuthorizationInterceptor,
                    new LogInterceptor());
        } else {
            API.init(Constants.API_BASE_URL, getSSLSocketFactory(), mAuthorizationInterceptor);
        }
        initDataBase();
    }

    private void initDataBase() {
        // 初始化，只需要调用一次
        AssetsDatabaseManager.initManager(this);
    }

    private AuthorizationInterceptor mAuthorizationInterceptor = new AuthorizationInterceptor() {
        @Override
        protected String getAuthorization() {
            return Account.preference().getToken();
        }

        @Override
        protected void onAuthorizationFailed() {
            LoginActivity.start(getApplicationContext(), false);
        }
    };

    private SSLSocketFactory getSSLSocketFactory() {
        return new HttpsSocketFactoryBuilder()
                .trust(getResources().openRawResource(R.raw.trust), "lyt2017")
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

    public IWXAPI getMsgApi() {
        if (msgApi == null) {
            msgApi = WXAPIFactory.createWXAPI(this, null);
            msgApi.registerApp(BuildConfig.WX_PAY_APPID);
        }
        return msgApi;
    }

    public Tencent getTencentApi() {
        if (mTencent == null)
            mTencent = Tencent.createInstance(BuildConfig.QQ_APPID, this);
        return mTencent;
    }
}