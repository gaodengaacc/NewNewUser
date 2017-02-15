package com.lyun.http;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZHAOWEIWEI on 2017/2/15.
 */

public abstract class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Headers headers = request.headers();
        request = request.newBuilder()
                .headers(headers)
                .headers(getHeaders())
                .build();
        return chain.proceed(request);
    }

    public abstract Headers getHeaders();
}
