package com.lyun.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.platform.Platform;
import okio.Buffer;

/**
 * Created by ZHAOWEIWEI on 2016/12/16.
 */

public class LogInterceptor implements Interceptor {

    private Gson gson;

    public LogInterceptor() {
        gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(request.method() + " " + request.url() + "\n");
        sb.append("-----------------= Request =-----------------\n");
        sb.append(request.headers() + "\n");
        sb.append(prettyJson(bodyToString(request.body())) + "\n");

        Response response = chain.proceed(request);

        sb.append("-----------------= Response =-----------------\n");
        String bodyString = response.body().string();
        sb.append(prettyJson(bodyString) + "\n");
        sb.append("\n");

        Platform.get().log(Platform.INFO, sb.toString(), null);

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
    }

    private String bodyToString(RequestBody body) throws IOException {
        if (body != null) {
            if (body instanceof MultipartBody) {
                return "MultipartBody";
            } else if (body instanceof FormBody) {
                return "FormBody";
            } else {
                Buffer buffer = new Buffer();
                body.writeTo(buffer);
                return buffer.readUtf8();
            }
        }
        return "";
    }

    private String prettyJson(final String json) {
        return gson.toJson(new JsonParser().parse(json));
    }
}
