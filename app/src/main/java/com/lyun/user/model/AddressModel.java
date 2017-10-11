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
    public Observable<APIResult> addAddress(AddAddressRequestBean bean) {
        return API.auth.addAddress(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<List<AddressItemResponse>> queryAddress(BaseRequestBean bean) {
        return parseAPIObservable(API.auth.queryAddress(bean).onErrorReturn(throwable -> ErrorParser.mockResult(throwable)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<APIResult> deleteAddress(DoAddressRequestBean bean) {
        return API.auth.deleteAddress(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<APIResult> defaultAddress(DoAddressRequestBean bean) {
        return API.auth.defaultAddress(bean).onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<APIResult> updateAddress(AddAddressRequestBean bean) {
        return API.auth.updateAddress(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
