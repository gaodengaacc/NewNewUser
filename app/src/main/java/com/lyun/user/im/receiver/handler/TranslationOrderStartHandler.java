package com.lyun.user.im.receiver.handler;

import com.google.gson.reflect.TypeToken;
import com.lyun.user.Account;
import com.lyun.user.AppApplication;
import com.lyun.user.im.receiver.attach.Attach;
import com.lyun.user.im.receiver.attach.TranslationOrderStart;
import com.lyun.user.model.TranslationOrderModel;
import com.lyun.user.service.TranslationOrderService;

import java.lang.reflect.Type;

/**
 * Created by ZHAOWEIWEI on 2017/3/2.
 */

public class TranslationOrderStartHandler implements AttachContentHandler<TranslationOrderStart> {

    @Override
    public void handleNotification(TranslationOrderStart data) {
        TranslationOrderService.start(AppApplication.getInstance(),
                data.getUserOrderId(), null,
                TranslationOrderModel.OrderType.MESSAGE,
                data.getOrderHand(),
                Account.preference().getPhone());
    }

    @Override
    public Type getDataType() {
        return new TypeToken<TranslationOrderStart>() {
        }.getType();
    }

    @Override
    public int getHandleType() {
        return Attach.Type.TRANSLATION_ORDER_START;
    }
}
