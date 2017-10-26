package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Build;
import android.view.View;

import com.lyun.library.mvvm.viewmodel.DialogViewModel;
import com.lyun.user.Constants;
import com.lyun.user.pay.PaySuccessInfo;
import com.lyun.utils.TimeUtil;

/**
 * @author Gordon
 * @since 2017/10/26
 * do()
 */

public class PaySuccessDialogViewModel extends DialogViewModel {
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> money = new ObservableField<>();
    public final ObservableField<String> orderId = new ObservableField<>();
    public final ObservableField<String> orderTime = new ObservableField<>();
    public final ObservableField<String> orderUserTime = new ObservableField<>();
    public final ObservableInt topVisible = new ObservableInt();//android 5.0以上显示，否则不显示

    public PaySuccessDialogViewModel(PaySuccessInfo paySuccessInfo) {
        init(paySuccessInfo);
    }

    private void init(PaySuccessInfo paySuccessInfo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topVisible.set(View.VISIBLE);
        } else {
            topVisible.set(View.GONE);
        }
        if (paySuccessInfo == null) return;
        imageUrl.set(Constants.IMAGE_BASE_URL + paySuccessInfo.imageUrl);
        money.set("￥" + paySuccessInfo.money + "元");
        orderId.set(paySuccessInfo.orderId);
        orderTime.set(paySuccessInfo.tradeTime);
        orderUserTime.set(TimeUtil.formatTime(paySuccessInfo.activeStartTime, "yyyy-MM-dd") + "至" + TimeUtil.formatTime(paySuccessInfo.activeEndTime, "yyyy-MM-dd"));
    }

    public void onClick(View view) {
        dismiss();
    }
}
