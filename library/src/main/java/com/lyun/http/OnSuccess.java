package com.lyun.http;

import io.reactivex.functions.Consumer;

/**
 * Created by ZHAOWEIWEI on 2017/1/5.
 */

public abstract class OnSuccess<T> implements Consumer<T> {

    @Override
    public void accept(T t) throws Exception {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);

}
