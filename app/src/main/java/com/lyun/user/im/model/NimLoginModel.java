package com.lyun.user.im.model;

import com.google.gson.Gson;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;

/**
 * Created by ZHAOWEIWEI on 2017/1/18.
 */

public class NimLoginModel {
    public void login() {
        NimUIKit.doLogin(new LoginInfo("15601920157", "4332weizi"), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                System.out.println(new Gson().toJson(loginInfo));
            }

            @Override
            public void onFailed(int i) {
                System.out.println("failed");
            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
    }
}
