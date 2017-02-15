package com.lyun.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
        Response response = chain.proceed(chain.request());
        System.out.println("\n");
        System.out.println(chain.request().method() + " " + chain.request().url());
        System.out.println("-----------------= Request =-----------------");
        System.out.println(chain.request().headers());
        System.out.println(prettyJson(bodyToString(chain.request())));
        System.out.println("-----------------= Response =-----------------");
        String bodyString = response.body().string();
        System.out.println(prettyJson(bodyString));
        System.out.println("\n");
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
    }

    private String bodyToString(Request request) {
        try {
            Request copy = request.newBuilder().build();
            Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    private String prettyJson(final String json) {
        return gson.toJson(new JsonParser().parse(json));
    }
}
