package com.lyun.user.im.receiver.handler;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.lyun.user.im.receiver.attach.Attach;
import com.lyun.user.im.receiver.attach.TranslationOrderFinish;
import com.lyun.user.service.TranslationOrder;
import com.lyun.user.service.TranslationOrderService;

import java.lang.reflect.Type;

/**
 * 用户端挂断翻译服务
 */

public class TranslationOrderFinishByUserHandler implements AttachContentHandler<TranslationOrderFinish> {

    @Override
    public void handleNotification(Context context, TranslationOrderFinish data) {
        TranslationOrderService.stop(context, data.getUserOrderId(), TranslationOrder.USER, "用户端中断了本次服务");
    }

    @Override
    public Type getDataType() {
        return new TypeToken<TranslationOrderFinish>() {
        }.getType();
    }

    @Override
    public int getHandleType() {
        return Attach.Type.TRANSLATION_ORDER_FINISH_BY_USER;
    }

}
