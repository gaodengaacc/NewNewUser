package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.fragment.ServiceCardFragment;

/**
 * @author Gordon
 * @since 2017/9/29
 * do()
 */

public class PaySuccessViewModel extends ViewModel {
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableInt money = new ObservableInt();
    public final ObservableField<String> orderId = new ObservableField<>();
    public final ObservableField<String> orderTime = new ObservableField<>();
    public final ObservableField<String> orderUserTime = new ObservableField<>();

    public PaySuccessViewModel(ServiceCardFragment.PaySuccessInfo paySuccessInfo) {
        init(paySuccessInfo);
    }

    private void init(ServiceCardFragment.PaySuccessInfo paySuccessInfo) {
        imageUrl.set(paySuccessInfo.imageUrl);
        money.set(paySuccessInfo.money);
        orderId.set(paySuccessInfo.orderId);
        orderTime.set(paySuccessInfo.orderTime);
        orderUserTime.set(paySuccessInfo.orderUserTime);
    }
}
