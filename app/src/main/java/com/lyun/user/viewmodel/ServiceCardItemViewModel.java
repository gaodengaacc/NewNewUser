package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.api.response.MyServiceCardResponse;
import com.lyun.utils.TimeUtil;

/**
 * @author Gordon
 * @since 2017/7/31
 * do()
 */

public class ServiceCardItemViewModel extends ViewModel {
    public final ObservableInt cardBg = new ObservableInt();
    public final ObservableField<String> onLineTime = new ObservableField<>();
    public final ObservableField<String> seniorTimes = new ObservableField<>();
    public final ObservableField<String> legalInstrumentsTimes = new ObservableField<>();
    public final ObservableField<String> legalLectureTimes = new ObservableField<>();
    public final ObservableField<String> orderId = new ObservableField<>();
    public final ObservableField<String> tradeTime = new ObservableField<>();
    public final ObservableField<String> userTime = new ObservableField<>();
    public final ObservableField<String> cardValue = new ObservableField<>();
    public final ObservableInt buyTextColor = new ObservableInt();
    public MyServiceCardResponse data;

    public ServiceCardItemViewModel(MyServiceCardResponse data) {
        this.data =data;
        init(data);
    }

    private void init(MyServiceCardResponse data) {
        switch (data.getCard().getId()) {
            case "1":
                cardBg.set(R.mipmap.bg_service_card_experience);
//                cardValue.set("VIP卡：9999元");
                buyTextColor.set(Color.parseColor("#c7cbe0"));
                break;
            case "2":
                cardBg.set(R.mipmap.bg_service_card_normal);
//                cardValue.set("普通卡：999元");
                buyTextColor.set(Color.parseColor("#4edec8"));
                break;
            case "3":
                cardBg.set(R.mipmap.bg_service_card_imp);
//                cardValue.set("贵宾卡：6999元");
                buyTextColor.set(Color.parseColor("#f6d592"));
                break;
            case "4":
                cardBg.set(R.mipmap.bg_service_card_vip);
//                cardValue.set("VIP卡：9999元");
                buyTextColor.set(Color.parseColor("#c7cbe0"));
                break;

            default:
                break;
        }
        orderId.set(data.getOrderNo());
        tradeTime.set(TimeUtil.formatTime(data.getTradeTime(), "yyyy-MM-dd"));
        userTime.set(TimeUtil.formatTime(data.getActiveStartTime(), "yyyy-MM-dd") + "至" + TimeUtil.formatTime(data.getActiveEndTime(), "yyyy-MM-dd"));
        onLineTime.set(data.getLeftTime() + "分钟");
        seniorTimes.set(data.getOnlineSeniorCounselAdviceTimes() + "次");
        legalInstrumentsTimes.set(data.getLegalInstrumentsDraftTimes() + "次");
        legalLectureTimes.set(data.getLegalLectureTimes() + "次");

    }

}
