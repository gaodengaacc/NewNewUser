package com.lyun.user.model;

import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.Account;
import com.lyun.user.api.API;
import com.lyun.user.api.request.GenerateOrderRequest;
import com.lyun.user.api.response.GenerateOrderResponse;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZHAOWEIWEI on 2017/2/16.
 */

public class TranslationOrderModel extends Model {

    public Observable<APIResult<GenerateOrderResponse>> generateOrder(String languageId, String orderTypeId) {
        GenerateOrderRequest request = new GenerateOrderRequest();
        request.setCardNo(Account.preference().getPhone());
        request.setLanguageId(languageId);
        request.setOrderTypeId(orderTypeId);
        return API.translationOrder.generateOrder(request)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

}
