package com.lyun.user.im.receiver.handler;

import android.content.Context;

import java.lang.reflect.Type;

/**
 * Created by ZHAOWEIWEI on 2017/3/2.
 */

public interface AttachContentHandler<T> {

    void handleNotification(Context context, T data);

    Type getDataType();

    int getHandleType();

}
