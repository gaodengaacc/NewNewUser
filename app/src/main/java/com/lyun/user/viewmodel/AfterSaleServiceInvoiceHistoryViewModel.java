package com.lyun.user.viewmodel;

import android.databinding.ObservableField;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.api.response.InvoiceHistoryResponse;
import com.lyun.utils.TimeUtil;

public class AfterSaleServiceInvoiceHistoryViewModel extends ViewModel {

    public final ObservableField<InvoiceHistoryResponse> data = new ObservableField<>();

    public final ObservableField<String> cardName = new ObservableField<>();
    public final ObservableField<String> cardImg = new ObservableField<>();
    public final ObservableField<String> cardPrice = new ObservableField<>();
    public final ObservableField<String> tradeTime = new ObservableField<>();
    public final ObservableField<String> orderId = new ObservableField<>();
    public final ObservableField<String> invoiceTime = new ObservableField<>();

    public AfterSaleServiceInvoiceHistoryViewModel(InvoiceHistoryResponse data) {
        this.data.set(data);
        cardName.set(data.getName());
        cardImg.set(data.getLogoImg());
        cardPrice.set("￥"+data.getPrice());
        tradeTime.set("成交日期：" + data.getTradeTime());
        orderId.set("订单编号：" + data.getOrderNo());
        invoiceTime.set("开票日期：" + data.getFilingTime());
    }
}