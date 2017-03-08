package com.lyun.user.im.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lyun.user.im.receiver.attach.Attach;
import com.lyun.user.im.receiver.handler.AttachContentHandler;
import com.lyun.user.im.receiver.handler.TranslationOrderFinishByTranslatorHandler;
import com.lyun.user.im.receiver.handler.TranslationOrderStartHandler;
import com.lyun.utils.L;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.msg.model.CustomNotification;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义通知消息广播接收器
 */
public class CustomNotificationReceiver extends BroadcastReceiver {

    private Map<Integer, AttachContentHandler> mNotificationHandlers = new HashMap<>();

    public CustomNotificationReceiver() {
        registerNotificationHandler(new TranslationOrderStartHandler());
        registerNotificationHandler(new TranslationOrderFinishByTranslatorHandler());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = context.getPackageName() + NimIntent.ACTION_RECEIVE_CUSTOM_NOTIFICATION;
        if (action.equals(intent.getAction())) {

            // 从intent中取出自定义通知
            CustomNotification notification = (CustomNotification) intent.getSerializableExtra(NimIntent.EXTRA_BROADCAST_MSG);

            try {
                JsonObject attach = new JsonParser().parse(notification.getContent()).getAsJsonObject();
                int type = attach.get("type").getAsInt();
                String content = attach.get("content").toString();

                if (mNotificationHandlers.containsKey(type)) {
                    AttachContentHandler handler = mNotificationHandlers.get(type);
                    handler.handleNotification(new Gson().fromJson(content, handler.getDataType()));
                }
            } catch (Exception e) {
                L.e(getClass().getSimpleName(), e);
            }

            L.d(getClass().getSimpleName(), "receive custom notification: " + notification.getContent() + " from :" + notification.getSessionId() + "/" + notification.getSessionType());
        }
    }

    private <T extends AttachContentHandler> void registerNotificationHandler(T handler) {
        mNotificationHandlers.put(handler.getHandleType(), handler);
    }
}
