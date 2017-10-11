package com.netease.nim.uikit;

import android.support.annotation.DrawableRes;

public interface UserInfoProvider extends com.netease.nimlib.sdk.uinfo.UserInfoProvider {

    /**
     * 如果根据用户账号找不到UserInfo的avatar时，显示的默认头像资源ID
     *
     * @return 默认头像的资源ID
     */
    @DrawableRes int getDefaultIconResId(String account);

}
