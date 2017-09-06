package com.lyun.user.viewmodel;

import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.api.response.OrderHistoryResponse;

import net.funol.databinding.watchdog.annotations.WatchThis;

public class AfterSaleServiceOrderHistoryViewModel extends ViewModel {

    public final ObservableField<OrderHistoryResponse> data = new ObservableField<>();

    public final ObservableField<String> cardName = new ObservableField<>();

    @WatchThis
    public final ObservableField navigateApplyForInvoice = new ObservableField();

    public AfterSaleServiceOrderHistoryViewModel(OrderHistoryResponse data) {
        this.data.set(data);
    }

    public final RelayCommand applyForInvoice = new RelayCommand(() -> ObservableNotifier.alwaysNotify(navigateApplyForInvoice, null));

}
