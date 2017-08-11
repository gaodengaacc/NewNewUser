package com.lyun.user.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.view.View;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.R;
import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.eventbusmessage.homefragment.EventHomePobDismissMessage;
import com.lyun.user.eventbusmessage.homefragment.EventPickMessage;
import com.lyun.user.eventbusmessage.homefragment.EventSelectMessage;
import com.lyun.user.eventbusmessage.homefragment.EventTranslationOrderMessage;
import com.lyun.user.model.RemainingTimeModel;
import com.lyun.user.model.TranslationOrderModel;
import com.lyun.user.model.TranslationOrderModel.OrderType;
import com.lyun.user.service.TranslationOrder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 郑成裕 on 2016/12/30.
 */

public class HomeFragmentViewModel extends ViewModel {

    public final ObservableInt imageViewModelChange = new ObservableInt();
    public final ObservableInt modelChange = new ObservableInt();
    public final ObservableField<String> textViewModelChange = new ObservableField<>();
    private OrderType mTranslationOrderType = OrderType.AUDIO;
    public final ObservableInt backGround = new ObservableInt();//背景
    public final ObservableInt selectIcon = new ObservableInt();//selector图片
    public final ObservableField<String> selectText = new ObservableField<>();//选择的图片
    public final ObservableInt textViewColor1 = new ObservableInt();//语音呼叫
    public final ObservableInt textViewColor2 = new ObservableInt();//图文翻译
    public final ObservableField<FindLanguageResponse> mCurrentLanguage = new ObservableField<>();//目标语言
    public final ObservableField<String> unusedTime = new ObservableField<>();//剩余时间
    private int unTime;
    public HomeFragmentViewModel() {
        EventBus.getDefault().register(this);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getRemainingTime(Account.preference().getPhone());//获取剩余时间
        Observable.empty()
                .delay(2, TimeUnit.SECONDS)
                .doOnComplete(() -> getRemainingTime(Account.preference().getPhone()))
                .subscribe();
    }

    private void getRemainingTime(String userName) {
        new RemainingTimeModel().getRemainingTime(userName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    if (apiResult.isSuccess()) {
                        unTime = Integer.parseInt(apiResult.getContent().toString());
                        if (unTime <= 0) {
                            unusedTime.set("剩余使用0分钟");
                        } else {
                            unusedTime.set("剩余使用" + unTime + "分钟");
                        }
                    } else {
                        getToast().setText(apiResult.getDescribe());
                    }
                });
    }

    public void initData() {//初始化数据
        backGround.set(R.mipmap.bg_home_fragment);
        modelChange.set(R.mipmap.bg_home_fragment_tab_left);
        imageViewModelChange.set(R.mipmap.icon_home_fragment_call);
        unusedTime.set("--");
        selectIcon.set(R.mipmap.icon_home_fragment_down);
        selectText.set("合同纠纷");
        textViewModelChange.set("语音呼叫");
        textViewColor1.set(Color.parseColor("#ffffff"));
        textViewColor2.set(Color.parseColor("#209ced"));

    }

    // 防止连续点击请求翻译
    public ObservableBoolean onRequestTranslationClickable = new ObservableBoolean(true);

    public RelayCommand onRequestTranslation = new RelayCommand(() -> {
        EventProgressMessage message = new EventProgressMessage(true);
        EventBus.getDefault().post(message);
        if (unTime <= 0) {
            EventBus.getDefault().post("您剩余的时间不足,请购买服务时间");
        } else {
            onRequestTranslationClickable.set(false);
            // 0=图文 1=语音
            final OrderType orderType = mTranslationOrderType;
            new TranslationOrderModel().generateOrder(mCurrentLanguage.get().getId() + "", orderType.getValue())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(orderId -> {
                                // 仅传递orderId、orderType、targetLanguage
                                TranslationOrder order = new TranslationOrder(orderId, orderType, mCurrentLanguage.get().getName(), 0, null, null);
                                message.setMessage(false);
                                EventBus.getDefault().post(message);
                                onRequestTranslationClickable.set(true);
                                EventBus.getDefault().post(new EventTranslationOrderMessage(order));
                            },
                            throwable -> {
                                message.setMessage(false);
                                EventBus.getDefault().post(message);
                                onRequestTranslationClickable.set(true);
                                EventToastMessage  toastMessage = new EventToastMessage();
                                toastMessage.setMessage(throwable.getMessage());
                                EventBus.getDefault().post(toastMessage);
                            });
        }

    });

    public void modelChangeImageViewClick(View view) {//选择翻译模式，语言或者图文
        if (mTranslationOrderType == OrderType.MESSAGE) {
            modelChange.set(R.mipmap.bg_home_fragment_tab_left);
            imageViewModelChange.set(R.mipmap.icon_home_fragment_call);
            textViewModelChange.set("语音呼叫");
            textViewColor1.set(Color.parseColor("#ffffff"));
            textViewColor2.set(Color.parseColor("#209ced"));
            mTranslationOrderType = OrderType.AUDIO;
        } else {
            modelChange.set(R.mipmap.bg_home_fragment_tab_right);
            imageViewModelChange.set(R.mipmap.icon_home_fragment_msg);
            textViewModelChange.set("图文翻译");
            textViewColor1.set(Color.parseColor("#209ced"));
            textViewColor2.set(Color.parseColor("#ffffff"));
            mTranslationOrderType = OrderType.MESSAGE;
        }
    }

    public void selectOnClick(View view) {
        selectIcon.set(R.mipmap.icon_home_fragment_up);
        EventBus.getDefault().post(new EventPickMessage(true));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pickSelectText(EventSelectMessage response) {
        mCurrentLanguage.set(response.getMessage());
        selectText.set(response.getMessage().getName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pubDismissListener(EventHomePobDismissMessage bean) {
        selectIcon.set(R.mipmap.icon_home_fragment_down);
    }

    public void unRegisterEventBus() {
        EventBus.getDefault().unregister(this);
    }
}
