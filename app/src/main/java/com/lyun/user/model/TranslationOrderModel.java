package com.lyun.user.model;

import com.lyun.api.ErrorParser;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.Account;
import com.lyun.user.api.API;
import com.lyun.user.api.request.CancelTranslationOrderBean;
import com.lyun.user.api.request.GenerateOrderRequest;
import com.lyun.user.api.request.HeartBeatBean;
import com.lyun.user.api.request.TranslatorStatusBean;
import com.lyun.user.api.response.TranslatorStatusResponse;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZHAOWEIWEI on 2017/2/16.
 */

public class TranslationOrderModel extends Model {

    public Observable<String> generateOrder(String languageId, String orderTypeId) {
        GenerateOrderRequest request = new GenerateOrderRequest();
        request.setCardNo(Account.preference().getPhone());
        request.setLanguageId(languageId);
        request.setOrderTypeId(orderTypeId);
        return parseAPIObservable(API.translationOrder.generateOrder(request).onErrorReturn(throwable -> ErrorParser.mockResult(throwable)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<APIResult> heartBeat(String userOrderId) {
        return API.translationOrder.heartBeat(new HeartBeatBean(userOrderId, Account.preference().getPhone()))
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<String> cancelOrder(String userOrderId) {
        return parseAPIObservable(API.translationOrder.cancelOrder(new CancelTranslationOrderBean(userOrderId))
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable)))
                .retryWhen(throwableObservable -> throwableObservable.zipWith(Observable.range(1, 3), (throwable, integer) -> integer))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<APIResult<TranslatorStatusResponse>> setTranslatorStatus(String userOrderId, String phoneState) {
        return API.translationOrder.setTranslatorStatus(new TranslatorStatusBean(userOrderId, phoneState))
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .retryWhen(throwableObservable -> throwableObservable.zipWith(Observable.range(1, 3), (throwable, integer) -> integer))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public enum OrderType implements Serializable {

        // 0=图文 1=语音
        MESSAGE("0"), AUDIO("1");

        private String value;

        OrderType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
