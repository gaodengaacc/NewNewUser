package com.lyun.user.model;

import com.lyun.api.ErrorParser;
import com.lyun.api.response.APIPageResult;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.InvoiceHistoryRequest;
import com.lyun.user.api.request.OrderHistoryRequest;
import com.lyun.user.api.response.InvoiceHistoryResponse;
import com.lyun.user.api.response.OrderHistoryResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class AfterSaleServiceModel extends Model {

    public Observable<APIResult<APIPageResult<List<OrderHistoryResponse>>>> getOrderHistory(int page) {
        OrderHistoryRequest request = new OrderHistoryRequest();
        request.setPageid(page);
        return API.afterSale.getOrderHistory(request)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<APIResult<APIPageResult<List<InvoiceHistoryResponse>>>> getInvoiceHistory(int page) {
        InvoiceHistoryRequest request = new InvoiceHistoryRequest();
        request.setPageid(page);
        return API.afterSale.getInvoiceHistory(request)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
