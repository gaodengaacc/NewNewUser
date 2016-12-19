package com.lyun.http.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by ZHAOWEIWEI on 2016/12/16.
 */

public class APIConverterFactory extends Converter.Factory {

    private Gson gson;

    public APIConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("parameter gson can not be null");
        this.gson = gson;
    }

    public static APIConverterFactory create() {
        return create(new Gson());
    }

    public static APIConverterFactory create(Gson gson) {
        return new APIConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new APIResponseBodyConverter(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new APIRequestBodyConverter(gson, adapter);
    }
}
