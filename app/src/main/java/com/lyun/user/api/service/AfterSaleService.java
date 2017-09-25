package com.lyun.user.api.service;

import com.lyun.api.response.APIPageResult;
import com.lyun.api.response.APIResult;
import com.lyun.user.api.APIConstants;
import com.lyun.user.api.request.ApplyForInvoiceRequest;
import com.lyun.user.api.request.InvoiceHistoryRequest;
import com.lyun.user.api.request.OrderHistoryRequest;
import com.lyun.user.api.response.InvoiceHistoryResponse;
import com.lyun.user.api.response.OrderHistoryResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AfterSaleService {

    @POST(APIConstants.QUERY_ORDER_HISTORY)
    Observable<APIResult<APIPageResult<List<OrderHistoryResponse>>>> getOrderHistory(@Body OrderHistoryRequest body);

    @POST(APIConstants.QUERY_INVOICE_HISTORY)
    Observable<APIResult<APIPageResult<List<InvoiceHistoryResponse>>>> getInvoiceHistory(@Body InvoiceHistoryRequest body);

    @POST(APIConstants.APPLY_FOR_INVOICE)
    Observable<APIResult<Object>> applyForInvoice(@Body ApplyForInvoiceRequest body);
}
