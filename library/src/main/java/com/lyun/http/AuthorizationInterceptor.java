package com.lyun.http;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lyun.api.response.APIResult;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by ZHAOWEIWEI on 2017/2/15.
 */

public abstract class AuthorizationInterceptor implements Interceptor {

    private final String TAG = getClass().getSimpleName();

    private final String STATUS_TOKEN_EXPIRED = APIResult.Status.STATUS_TOKEN_EXPIRED;

    protected JsonParser mJsonParser;

    public AuthorizationInterceptor() {
        mJsonParser = new JsonParser();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Headers originHeaders = request.headers();

        Headers newHeaders = new Headers.Builder()
                .add("Authorization", getAuthorization() + "")
                .build();

        request = request.newBuilder()
                .headers(originHeaders)
                .headers(newHeaders)
                .build();

        Response response = chain.proceed(request);

        String result = response.body().string();

        JsonObject object = mJsonParser.parse(result).getAsJsonObject();
        if (STATUS_TOKEN_EXPIRED.equals(object.get("status").getAsString())) {
            onAuthorizationFailed();
        }

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), result))
                .build();
    }

    protected abstract String getAuthorization();

    protected abstract void onAuthorizationFailed();

}
