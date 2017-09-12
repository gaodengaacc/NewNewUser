package com.lyun.user.im;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.lyun.user.R;
import com.netease.nim.uikit.ImageLoaderKit;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;

public class NimUserInfoProvider implements UserInfoProvider {

    private Context context;

    public NimUserInfoProvider(Context context) {
        this.context = context;
    }

    @Override
    public UserInfoProvider.UserInfo getUserInfo(String account) {
        UserInfo user = NimUserInfoCache.getInstance().getUserInfo(account);
        if (user == null) {
            NimUserInfoCache.getInstance().getUserInfoFromRemote(account, null);
        }
        return user;
    }

    @Override
    public int getDefaultIconResId() {
        return R.mipmap.user_default_avatar;
    }

    @Override
    public Bitmap getTeamIcon(String tid) {
        // 从内存缓存中查找群头像
        Team team = TeamDataCache.getInstance().getTeamById(tid);
        if (team != null) {
            Bitmap bm = ImageLoaderKit.getNotificationBitmapFromCache(team.getIcon());
            if (bm != null) {
                return bm;
            }
        }

        // 默认图
        Drawable drawable = context.getResources().getDrawable(com.netease.nim.uikit.R.drawable.nim_avatar_group);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        return null;
    }

    @Override
    public Bitmap getAvatarForMessageNotifier(String account) {
        UserInfo user = getUserInfo(account);
        return (user != null) ? ImageLoaderKit.getNotificationBitmapFromCache(user.getAvatar()) : null;
    }

    @Override
    public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                   SessionTypeEnum sessionType) {
        String nick = null;
        if (sessionType == SessionTypeEnum.P2P) {
            nick = NimUserInfoCache.getInstance().getAlias(account);
        } else if (sessionType == SessionTypeEnum.Team) {
            nick = TeamDataCache.getInstance().getTeamNick(sessionId, account);
            if (TextUtils.isEmpty(nick)) {
                nick = NimUserInfoCache.getInstance().getAlias(account);
            }
        }
        // 返回null，交给sdk处理。如果对方有设置nick，sdk会显示nick
        if (TextUtils.isEmpty(nick)) {
            return null;
        }

        return nick;
    }
}
