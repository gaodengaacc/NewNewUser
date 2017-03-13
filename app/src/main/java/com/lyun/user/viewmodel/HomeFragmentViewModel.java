package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.R;
import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.user.model.RemainingTimeModel;
import com.lyun.user.model.TranslationOrderModel;
import com.lyun.user.model.TranslationOrderModel.OrderType;
import com.lyun.user.service.TranslationOrder;

import net.funol.databinding.watchdog.annotations.WatchThis;

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
    public final ObservableInt textViewColor1 = new ObservableInt();//语音呼叫
    public final ObservableInt textViewColor2 = new ObservableInt();//图文翻译
    public final ObservableField<FindLanguageResponse> mCurrentLanguage = new ObservableField<>();//目标语言
    public final ObservableField<Typeface> typeface1 = new ObservableField<>();//设置字体加粗
    public final ObservableField<Typeface> typeface2 = new ObservableField<>();
    public final ObservableField<String> unusedTime = new ObservableField<>();//剩余时间

    @WatchThis
    public final BaseObservable onPickLanguage = new BaseObservable();
    @WatchThis
    public final ObservableField<TranslationOrder> onTranslationOrderGenerated = new ObservableField<>();
    @WatchThis
    public final ObservableField<String> onTranslationOrderGenerateFail = new ObservableField<>();
    @WatchThis
    public final ObservableBoolean progressDialogShow = new ObservableBoolean();


    public HomeFragmentViewModel() {
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getRemainingTime(Account.preference().getPhone());//获取剩余时间
    }

    private void getRemainingTime(String userName) {
        new RemainingTimeModel().getRemainingTime(userName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    if (apiResult.isSuccess()) {
                        if (Integer.parseInt(apiResult.getContent().toString()) <= 0) {
                            unusedTime.set("0");
                        } else {
                            unusedTime.set(apiResult.getContent().toString());
                        }
                    } else {
                        getToast().setText(apiResult.getDescribe());
                    }
                });
    }

    public void initData() {//初始化数据
        backGround.set(R.mipmap.bg_fragment_specialist_translation);
        modelChange.set(R.mipmap.radio_green_fragment_specialist_translation);
        imageViewModelChange.set(R.mipmap.call_fragment_specialist_translation);
        unusedTime.set("--");
        textViewModelChange.set("语音呼叫");
        textViewColor1.set(Color.parseColor("#40d12d"));
        textViewColor2.set(Color.parseColor("#333333"));
        typeface1.set(Typeface.defaultFromStyle(Typeface.BOLD));
        typeface2.set(Typeface.defaultFromStyle(Typeface.NORMAL));

    }

    // 防止连续点击请求翻译
    public ObservableBoolean onRequestTranslationClickable = new ObservableBoolean(true);

    public RelayCommand onRequestTranslation = new RelayCommand(() -> {
        progressDialogShow.set(true);
        if ("0".equals(unusedTime.get())) {
            progressDialogShow.set(false);
            ObservableNotifier.alwaysNotify(onTranslationOrderGenerateFail, "您剩余的时间不足,请购买服务时间");
        } else {
            onRequestTranslationClickable.set(false);
            // 0=图文 1=语音
            final OrderType orderType = mTranslationOrderType;
            new TranslationOrderModel().generateOrder(mCurrentLanguage.get().getId() + "", orderType.getValue())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(orderId -> {
                                // 仅传递orderId、orderType、targetLanguage
                                TranslationOrder order = new TranslationOrder(orderId, orderType, mCurrentLanguage.get().getName(), 0, null, null);
                                progressDialogShow.set(false);
                                onRequestTranslationClickable.set(true);
                                ObservableNotifier.alwaysNotify(onTranslationOrderGenerated, order);
                            },
                            throwable -> {
                                progressDialogShow.set(false);
                                onRequestTranslationClickable.set(true);
                                ObservableNotifier.alwaysNotify(onTranslationOrderGenerateFail, throwable.getMessage());
                            });
        }

    });

    //选取目标语言
    public RelayCommand onPickLanguageClickCommand = new RelayCommand(() -> {
        onPickLanguage.notifyChange();
    });

    public void modelChangeImageViewClick(View view) {//选择翻译模式，语言或者图文
        if (mTranslationOrderType == OrderType.MESSAGE) {
            modelChange.set(R.mipmap.radio_green_fragment_specialist_translation);
            imageViewModelChange.set(R.mipmap.call_fragment_specialist_translation);
            textViewModelChange.set("语音呼叫");
            textViewColor1.set(Color.parseColor("#40d12d"));
            textViewColor2.set(Color.parseColor("#333333"));
            typeface1.set(Typeface.defaultFromStyle(Typeface.BOLD));
            typeface2.set(Typeface.defaultFromStyle(Typeface.NORMAL));
            mTranslationOrderType = OrderType.AUDIO;
        } else {
            modelChange.set(R.mipmap.radio_brown_fragment_specialist_translation);
            imageViewModelChange.set(R.mipmap.picture_fragment_specialist_translation);
            textViewModelChange.set("图文翻译");
            textViewColor1.set(Color.parseColor("#333333"));
            textViewColor2.set(Color.parseColor("#ffb900"));
            typeface1.set(Typeface.defaultFromStyle(Typeface.NORMAL));
            typeface2.set(Typeface.defaultFromStyle(Typeface.BOLD));
            mTranslationOrderType = OrderType.MESSAGE;
        }
    }
}
