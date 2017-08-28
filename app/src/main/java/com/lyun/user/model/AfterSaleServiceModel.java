package com.lyun.user.model;

import com.lyun.api.response.APIPageResult;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.response.InvoiceHistoryResponse;
import com.lyun.user.api.response.OrderHistoryResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class AfterSaleServiceModel extends Model {

    public Observable<APIResult<APIPageResult<List<OrderHistoryResponse>>>> getOrderHistory(int page) {
        APIResult<APIPageResult<List<OrderHistoryResponse>>> result = new APIResult<>();
        result.setStatus("0");
        result.setDescribe("success");
        APIPageResult<List<OrderHistoryResponse>> pageResult = new APIPageResult<>();
        pageResult.setPagecount(1);
        List<OrderHistoryResponse> list = new ArrayList<>();
        list.add(new OrderHistoryResponse());
        list.add(new OrderHistoryResponse());
        list.add(new OrderHistoryResponse());
        pageResult.setData(list);
        result.setContent(pageResult);
        return Observable.just(result);
    }

    public Observable<APIResult<APIPageResult<List<InvoiceHistoryResponse>>>> getInvoiceHistory(int page) {
        APIResult<APIPageResult<List<InvoiceHistoryResponse>>> result = new APIResult<>();
        result.setStatus("0");
        result.setDescribe("success");
        APIPageResult<List<InvoiceHistoryResponse>> pageResult = new APIPageResult<>();
        pageResult.setPagecount(1);
        List<InvoiceHistoryResponse> list = new ArrayList<>();
        list.add(new InvoiceHistoryResponse());
        list.add(new InvoiceHistoryResponse());
        list.add(new InvoiceHistoryResponse());
        pageResult.setData(list);
        result.setContent(pageResult);
        return Observable.just(result);
    }
}
