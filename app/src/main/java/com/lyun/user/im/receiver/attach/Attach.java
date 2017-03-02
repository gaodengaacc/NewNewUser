package com.lyun.user.im.receiver.attach;

import java.io.Serializable;

/**
 * Created by ZHAOWEIWEI on 2017/3/2.
 */

public class Attach<T> implements Serializable {

    /**
     * type : 1
     * content : {"userOrderId":"17837193445125918720145046","orderTypeId":"","orderHand":"15601920157"}
     */

    private int type;
    private T content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public static class Type {
        public final static int TRANSLATION_ORDER_START = 1;
    }
}
