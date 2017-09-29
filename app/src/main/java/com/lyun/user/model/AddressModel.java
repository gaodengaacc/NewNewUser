package com.lyun.user.model;

import com.lyun.api.ErrorParser;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.AddAddressRequestBean;
import com.lyun.user.api.request.BaseRequestBean;
import com.lyun.user.api.request.DoAddressRequestBean;
import com.lyun.user.api.response.AddressItemResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Gordon
 * @since 2017/8/25
 * do()
 */

public class AddressModel extends Model {
    public Observable<Integer> addAddress(AddAddressRequestBean bean) {
        return parseNullObservable(API.auth.addAddress(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<List<AddressItemResponse>> queryAddress(BaseRequestBean bean) {
        return parseAPIObservable(API.auth.queryAddress(bean).onErrorReturn(throwable -> ErrorParser.mockResult(throwable)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<Object> deleteAddress(DoAddressRequestBean bean) {
        return parseNullObservable(API.auth.deleteAddress(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<Object> defaultAddress(DoAddressRequestBean bean) {
        return parseNullObservable(API.auth.defaultAddress(bean).onErrorReturn(throwable -> ErrorParser.mockResult(throwable)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<Integer> updateAddress(AddAddressRequestBean bean) {
        return parseNullObservable(API.auth.updateAddress(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
