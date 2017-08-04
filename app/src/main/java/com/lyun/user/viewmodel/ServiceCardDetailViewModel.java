package com.lyun.user.viewmodel;

import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.eventbusmessage.EventActivityFinishMessage;
import com.lyun.user.eventbusmessage.cardpay.EventShowPayDialogMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Gordon
 * @since 2017/7/31
 * do()
 */

public class ServiceCardDetailViewModel extends ViewModel {


    public ServiceCardDetailViewModel(int cardId){
        init(cardId);
    }

    private void init(int cardId) {
        switch (cardId){
            case 0:
                topBg.set(R.mipmap.bg_card_detail_top_normal);
                break;
            case 1:
                topBg.set(R.mipmap.bg_card_detail_top_imp);
                break;
            case 2:
                topBg.set(R.mipmap.bg_card_detail_top_vip);
                break;
        }
    }

    public final ObservableInt topBg = new ObservableInt();

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.card_detail_back:
                EventBus.getDefault().post(new EventActivityFinishMessage());
                break;
            case R.id.card_detail_buy:
                EventBus.getDefault().post(new EventShowPayDialogMessage(999.0));
                break;
            default:
                break;
        }
    }
}
