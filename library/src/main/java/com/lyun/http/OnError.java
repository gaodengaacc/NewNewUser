package com.lyun.http;

import io.reactivex.functions.Consumer;

/**
 * Created by ZHAOWEIWEI on 2017/1/5.
 */

public abstract class OnError implements Consumer<Throwable> {

    @Override
    public void accept(Throwable t) throws Exception {
        onError(t);
    }

    public abstract void onError(Throwable t);
}
