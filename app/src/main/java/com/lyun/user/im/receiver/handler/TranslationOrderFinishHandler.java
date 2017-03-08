package com.lyun.user.im.receiver.handler;

import android.content.Intent;

import com.google.gson.reflect.TypeToken;
import com.lyun.user.AppApplication;
import com.lyun.user.im.receiver.attach.TranslationOrderFinish;
import com.lyun.user.service.TranslationOrder;
import com.lyun.user.service.TranslationOrderService;

import java.lang.reflect.Type;

/**
 * 翻译端挂断翻译服务
 */

public class TranslationOrderFinishHandler implements AttachContentHandler<TranslationOrderFinish> {

    @Override
    public void handleNotification(TranslationOrderFinish data) {
        Intent intent = new Intent(AppApplication.getInstance(), TranslationOrderService.class);
        AppApplication.getInstance().stopService(intent);
    }

    @Override
    public Type getDataType() {
        return new TypeToken<TranslationOrderFinish>() {
        }.getType();
    }

    @Override
    public int getHandleType() {
        return 3;
    }

}
