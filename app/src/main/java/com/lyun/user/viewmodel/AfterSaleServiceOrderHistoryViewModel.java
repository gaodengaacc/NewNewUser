package com.lyun.user.viewmodel;

import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

public class AfterSaleServiceOrderHistoryViewModel extends ViewModel {

    @WatchThis
    public final ObservableField navigateApplyForInvoice = new ObservableField();

    public final RelayCommand applyForInvoice = new RelayCommand(() -> ObservableNotifier.alwaysNotify(navigateApplyForInvoice, null));

}
