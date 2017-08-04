package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.api.response.ServiceCardListItemResponse;

/**
 * @author Gordon
 * @since 2017/7/31
 * do()
 */

public class ServiceCardItemViewModel extends ViewModel {
    public final ObservableInt cardBg = new ObservableInt();
    public final ObservableField<String> cardValue = new ObservableField<>();
    public final ObservableInt buyTextColor = new ObservableInt();
    public ServiceCardListItemResponse data;
    public ServiceCardItemViewModel(ServiceCardListItemResponse data){
        this.data =data;
        init(data);
    }

    private void init(ServiceCardListItemResponse data) {
        switch(data.getCardId()){
            case 0:
                cardBg.set(R.mipmap.bg_service_card_normal);
                cardValue.set("普通卡：999元");
                buyTextColor.set(Color.parseColor("#4edec8"));
                break;
            case 1:
                cardBg.set(R.mipmap.bg_service_card_imp);
                cardValue.set("贵宾卡：6999元");
                buyTextColor.set(Color.parseColor("#f6d592"));
                break;
            case 2:
                cardBg.set(R.mipmap.bg_service_card_vip);
                cardValue.set("VIP卡：9999元");
                buyTextColor.set(Color.parseColor("#c7cbe0"));
                break;
            default:
                break;
        }
    }

}
