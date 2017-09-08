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
        cardName.set(data.getCard().getName());
        cardImg.set(data.getCard().getLogoImg());
        cardPrice.set(data.getAmount() == 0 ? "免费" : "￥" + data.getAmount());
        tradeTime.set("成交日期：" + TimeUtil.formatTime(data.getTradeTime(),"yyyy-MM-dd HH:mm"));
        orderId.set("订单编号：" + data.getOrderNo());
        invoiceTime.set("开票日期：" + TimeUtil.formatTime(data.getFilingTime(),"yyyy-MM-dd HH:mm"));
    }
}
