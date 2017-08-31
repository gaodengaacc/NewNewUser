package com.lyun.user.model;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lyun.api.ErrorParser;
import com.lyun.api.response.APIResult;
import com.lyun.http.APIClient;
import com.lyun.http.converter.APIConverterFactory;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.Constants;
import com.lyun.user.api.API;
import com.lyun.user.api.request.BaseRequestBean;
import com.lyun.user.api.request.LoginBean;
import com.lyun.user.api.request.ThirdLoginBean;
import com.lyun.user.api.response.AccountBindResponse;
import com.lyun.user.api.response.LoginResponse;
import com.lyun.user.api.response.WxOpenIdResponse;
import com.lyun.user.api.service.AuthService;
import com.lyun.utils.MD5Util;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static com.lyun.http.APIClient.getTrustManager;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public class LoginModel extends Model {

    public Observable<APIResult<LoginResponse>> login(String username, String password) {
        LoginBean bean = new LoginBean(username, MD5Util.getStringMD5(password));
        return  API.auth.login(bean).onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<APIResult<LoginResponse>> login(String openId) {
        return   API.auth.login(new ThirdLoginBean(openId)).onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<List<AccountBindResponse>> relevanceThird() {
        return parseAPIObservable(API.auth.relevanceThird(new BaseRequestBean()).onErrorReturn(throwable -> ErrorParser.mockResult(throwable)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
    public Observable<WxOpenIdResponse> getWxOpenId(String appid,String secret,String code){
        Map<String,String> map = new HashMap<>();
        map.put("appid",appid);
        map.put("secret",secret);
        map.put("code",code);
        map.put("grant_type","authorization_code");
        return getRetrofit().create(AuthService.class).getOpenId(map)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    private Retrofit getRetrofit() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.WX_GET_OPENID_URL)
                .addConverterFactory(APIConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder()
                        .sslSocketFactory(APIClient.getSSLSocketFactory(), getTrustManager())
                        .hostnameVerifier(new AllowAllHostnameVerifier())
                        .build())
                .build();
          return mRetrofit;
    }
}
