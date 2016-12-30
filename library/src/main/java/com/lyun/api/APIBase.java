package com.lyun.api;

import com.lyun.http.APIClient;

import okhttp3.Interceptor;

/**
 * API的基类，包含一个创建接口实例的方法{@link #create(Class)}
 *
 * @author 赵尉尉
 * @date 2016/12/20
 */
public class APIBase {

    /**
     * 初始化API
     *
     * @param baseUrl
     */
    public static void init(String baseUrl) {
        APIClient.init(baseUrl);
    }

    /**
     * 初始化API
     *
     * @param baseUrl
     * @param interceptor
     */
    public static void init(String baseUrl, Interceptor interceptor) {
        APIClient.init(baseUrl, interceptor);
    }

    /**
     * 根据传入的Service接口，创建接口实例
     *
     * @param service
     * @param <T>
     * @return
     */
    protected static <T> T create(Class<? extends T> service) {
        return APIClient.client()
                .retrofit()
                .create(service);
    }

}