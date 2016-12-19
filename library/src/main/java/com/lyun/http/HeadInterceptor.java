package com.lyun.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZHAOWEIWEI on 2016/12/16.
 */

public class HeadInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .removeHeader("Content-Type")
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .build();
        return chain.proceed(request);
    }

}
