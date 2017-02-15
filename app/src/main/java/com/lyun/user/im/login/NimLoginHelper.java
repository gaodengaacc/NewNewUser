package com.lyun.user.im.login;

import android.content.res.Resources;

import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.im.NimCache;
import com.lyun.user.im.config.preference.Preferences;
import com.lyun.user.im.config.preference.UserPreferences;
import com.netease.nim.uikit.LoginSyncDataStatusObserver;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.ui.drop.DropManager;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import io.reactivex.Observable;

/**
 * Created by ZHAOWEIWEI on 2017/1/22.
 */

public class NimLoginHelper {

    public static Observable<LoginInfo> login(String account, String token) {
        return Observable.create(consumer -> {
            NIMClient.getService(AuthService.class)
                    .login(new LoginInfo(account, token))
                    .setCallback(new RequestCallback<LoginInfo>() {

                        @Override
                        public void onSuccess(LoginInfo param) {
                            NimCache.setAccount(account);
                            Preferences.saveUserAccount(account);
                            Preferences.saveUserToken(token);

                            // 初始化消息提醒配置
                            initNotificationConfig();

                            consumer.onNext(param);
                        }

                        @Override
                        public void onFailed(int code) {
                            if (code == 302 || code == 404) {
                                consumer.onError(new Exception(AppApplication.getInstance().getResources().getString(R.string.login_failed)));
                            } else {
                                consumer.onError(new Exception("登录失败: " + code));
                            }
                            consumer.onComplete();
                        }

                        @Override
                        public void onException(Throwable exception) {
                            consumer.onError(exception);
                            consumer.onComplete();
                        }
                    });

        });
    }

    private static void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = NimCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }

    public static void logout() {
        // 清理缓存&注销监听&清除状态
        NimUIKit.clearCache();
        // ChatRoomHelper.logout();
        NimCache.clear();
        LoginSyncDataStatusObserver.getInstance().reset();
        DropManager.getInstance().destroy();
    }
}
