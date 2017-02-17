package com.lyun.user.model;

import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.Account;
import com.lyun.user.api.API;
import com.lyun.user.api.request.GenerateOrderRequest;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZHAOWEIWEI on 2017/2/16.
 */

public class TranslationOrderModel extends Model {

    public Observable<APIResult<String>> generateOrder(String languageId, String orderTypeId) {
        GenerateOrderRequest request = new GenerateOrderRequest();
        request.setCardNo(Account.preference().getPhone());
        request.setLanguageId(languageId);
        request.setOrderTypeId(orderTypeId);
        return API.translationOrder.generateOrder(request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public enum OrderType {

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
