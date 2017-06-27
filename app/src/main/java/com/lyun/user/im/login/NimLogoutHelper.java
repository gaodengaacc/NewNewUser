package com.lyun.user.im.login;

import com.lyun.user.im.NimCache;
import com.netease.nim.uikit.LoginSyncDataStatusObserver;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.ui.drop.DropManager;

/**
 * 注销帮助类
 * Created by huangjun on 2015/10/8.
 */
public class NimLogoutHelper {
    public static void logout() {
        // 清理缓存&注销监听&清除状态
        NimUIKit.clearCache();
        // ChatRoomHelper.logout();
        NimCache.clear();
        LoginSyncDataStatusObserver.getInstance().reset();
        DropManager.getInstance().destroy();
    }
}
