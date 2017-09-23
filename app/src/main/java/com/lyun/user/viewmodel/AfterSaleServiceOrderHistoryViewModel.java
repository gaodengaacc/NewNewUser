package com.lyun.user.viewmodel;

import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Constants;
import com.lyun.user.api.response.OrderHistoryResponse;
import com.lyun.utils.TimeUtil;

import net.funol.databinding.watchdog.annotations.WatchThis;

public class AfterSaleServiceOrderHistoryViewModel extends ViewModel {

    public final ObservableField<OrderHistoryResponse> data = new ObservableField<>();

    public final ObservableField<String> cardName = new ObservableField<>();
    public final ObservableField<String> cardImg = new ObservableField<>();
    public final ObservableField<String> cardPrice = new ObservableField<>();
    public final ObservableField<String> tradeTime = new ObservableField<>();
    public final ObservableField<String> orderId = new ObservableField<>();

    @WatchThis
    public final ObservableField navigateApplyForInvoice = new ObservableField();

    public AfterSaleServiceOrderHistoryViewModel(OrderHistoryResponse data) {
        this.data.set(data);
        cardName.set(data.getName());
        cardImg.set(Constants.IMAGE_BASE_URL + data.getLogoImg());
        cardPrice.set("￥" + data.getPrice());
        tradeTime.set("成交日期：" + TimeUtil.formatTime(data.getTradeTime(), "yyyy-MM-dd HH:mm"));
        orderId.set("订单编号：" + data.getOrderNo());
    }

    public final RelayCommand applyForInvoice = new RelayCommand(() -> ObservableNotifier.alwaysNotify(navigateApplyForInvoice, null));

}
