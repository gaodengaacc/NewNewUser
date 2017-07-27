package com.lyun.user.EventBusMessage.homefragment;

import com.lyun.user.EventBusMessage.EventMessage;
import com.lyun.user.service.TranslationOrder;

/**
 * @author Gordon
 * @since 2017/7/27
 * do()
 */

public class EventTranslationOrderMessage implements EventMessage<TranslationOrder> {
    private  TranslationOrder translationOrder;
    @Override
    public void setMessage(TranslationOrder o) {
          translationOrder = o;
    }

    @Override
    public TranslationOrder getMessage() {
        return translationOrder;
    }
}
