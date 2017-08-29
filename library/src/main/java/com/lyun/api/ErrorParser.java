package com.lyun.api;

import com.google.gson.JsonSyntaxException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.lyun.api.exception.APIContentNullException;
import com.lyun.api.exception.APINotSuccessException;
import com.lyun.api.response.APIResult;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by ZHAOWEIWEI on 2017/1/5.
 */

public class ErrorParser {

    public static String parse(Throwable throwable) {
        if (throwable instanceof JsonSyntaxException) {
            return "服务器返回数据格式有误";
        } else if (throwable instanceof SocketTimeoutException) {
            return "网络连接超时";
        } else if (throwable instanceof IOException) {
            return "连接失败";
        } else if (throwable instanceof UnknownHostException) {
            return "服务器异常";
        } else if (throwable instanceof HttpException) {
            return "服务暂不可用";
        } else if (throwable instanceof APINotSuccessException) {
            return throwable.getMessage() == null ? "查询失败" : throwable.getMessage();
        } else if (throwable instanceof APIContentNullException) {
            return throwable.getMessage() == null ? "数据为空" : throwable.getMessage();
        }
        return "未知错误";
    }
    public static <T> APIResult<T> mockResult(Throwable throwable) {
        return new APIResult("-1", parse(throwable), null);
    }
}
