package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.R;
import com.lyun.user.model.RemainingTimeModel;
import com.lyun.user.model.TranslationOrderModel;
import com.lyun.user.model.TranslationOrderModel.OrderType;
import com.lyun.utils.filecache.Cache;
import com.lyun.utils.filecache.CacheUtil;

import net.funol.databinding.watchdog.annotations.WatchThis;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by 郑成裕 on 2016/12/30.
 */

public class HomeFragmentViewModel extends ViewModel {

    public final ObservableInt imageViewModelChange = new ObservableInt();
    public final ObservableInt modelChange = new ObservableInt();
    public final ObservableField<String> textViewModelChange = new ObservableField<>();
    private OrderType mTranslationOrderType = OrderType.MESSAGE;

    public final ObservableInt textViewColor1 = new ObservableInt();//语音呼叫
    public final ObservableInt textViewColor2 = new ObservableInt();//图文翻译
    public final ObservableField<String> textViewTargetLanguage = new ObservableField<>();//目标语言
    public final ObservableField<Typeface> typeface1 = new ObservableField<>();//设置字体加粗
    public final ObservableField<Typeface> typeface2 = new ObservableField<>();
    public final ObservableField<String> unUserTime = new ObservableField<>();//剩余时间

    private List list;

    @WatchThis
    public final ObservableField<OrderType> onTranslationOrderGenerated = new ObservableField<>();

    LanguagePickerDialogViewModel languagePickerDialogViewModel;


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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    unUserTime.set(apiResult.getContent().toString());
                });
    }

    public void initData() {//初始化数据
        modelChange.set(R.mipmap.radio_green_fragment_specialist_translation);
        imageViewModelChange.set(R.mipmap.call_fragment_specialist_translation);
        textViewModelChange.set("语音呼叫");
        textViewColor1.set(Color.parseColor("#40d12d"));
        textViewColor2.set(Color.parseColor("#333333"));
        typeface1.set(Typeface.defaultFromStyle(Typeface.BOLD));
        typeface2.set(Typeface.defaultFromStyle(Typeface.NORMAL));
        textViewTargetLanguage.set("英语");
    }

    public RelayCommand onRequestTranslation = new RelayCommand(() -> {
        // 0=图文 1=语音
        new TranslationOrderModel().generateOrder(textViewTargetLanguage.get(), mTranslationOrderType.getValue())
                .subscribe();
        onTranslationOrderGenerated.set(mTranslationOrderType);
        onTranslationOrderGenerated.notifyChange();//打开聊天框
    });

    public void languagePickerLinearLayoutClick(View view) {//选取目标语言
        if (languagePickerDialogViewModel == null) {
            CacheUtil.getInstance().getData(Cache.DATA_TYPE_FIND_BY_LANGUAGE, result -> {
                if (result != null)
                    list = (List) result;
            });
            languagePickerDialogViewModel = new LanguagePickerDialogViewModel(view.getContext(), list);
        }

        languagePickerDialogViewModel.setOnLanguagePickedListener(language -> textViewTargetLanguage.set(language));
        languagePickerDialogViewModel.show();
    }

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
