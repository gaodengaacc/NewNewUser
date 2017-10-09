package com.lyun.user.viewmodel;

import android.databinding.ObservableField;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Constants;
import com.lyun.user.pay.PaySuccessInfo;
import com.lyun.utils.TimeUtil;

/**
 * @author Gordon
 * @since 2017/9/29
 * do()
 */

public class PaySuccessViewModel extends ViewModel {
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> money = new ObservableField<>();
    public final ObservableField<String> orderId = new ObservableField<>();
    public final ObservableField<String> orderTime = new ObservableField<>();
    public final ObservableField<String> orderUserTime = new ObservableField<>();

    public PaySuccessViewModel(PaySuccessInfo paySuccessInfo) {
        init(paySuccessInfo);
    }

    private void init(PaySuccessInfo paySuccessInfo) {
        imageUrl.set(Constants.IMAGE_BASE_URL + paySuccessInfo.imageUrl);
        money.set("￥" + paySuccessInfo.money + "元");
        orderId.set(paySuccessInfo.orderId);
        orderTime.set(paySuccessInfo.tradeTime);
        orderUserTime.set(TimeUtil.formatTime(paySuccessInfo.activeStartTime, "yyyy-MM-dd") + "至" + TimeUtil.formatTime(paySuccessInfo.activeEndTime, "yyyy-MM-dd"));
    }
}
