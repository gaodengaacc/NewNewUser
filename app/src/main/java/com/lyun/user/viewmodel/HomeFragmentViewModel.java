package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.view.View;
import android.view.ViewTreeObserver;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.model.TranslationOrderModel;
import com.lyun.user.model.TranslationOrderModel.OrderType;

import net.funol.databinding.watchdog.annotations.WatchThis;

/**
 * Created by 郑成裕 on 2016/12/30.
 */

public class HomeFragmentViewModel extends ViewModel {

    public final ObservableInt imageViewModelChange = new ObservableInt();
    public final ObservableInt modelChange = new ObservableInt();
    public final ObservableField<String> textViewModelChange = new ObservableField<>();
    private OrderType mTranslationOrderType = OrderType.MESSAGE;
    public final ObservableField<String> textViewCategoryChange = new ObservableField<>();//服务类型
    public final ObservableField<Boolean> showPopupWindow = new ObservableField<>();
    private final int REQUEST_CODE = 10000;
    public final ObservableField<ViewTreeObserver.OnGlobalLayoutListener> linearLayoutListener = new ObservableField<>();

    public final ObservableInt textViewColor1 = new ObservableInt();//语音呼叫
    public final ObservableInt textViewColor2 = new ObservableInt();//图文翻译
    public final ObservableField<String> textViewTargetLanguage = new ObservableField<>();//目标语言

    @WatchThis
    public final ObservableField<OrderType> onTranslationOrderGenerated = new ObservableField<>();

    LanguagePickerDialogViewModel languagePickerDialogViewModel;

    public HomeFragmentViewModel() {
        initData();
    }

    public void initData() {//初始化数据
        modelChange.set(R.mipmap.radio_green_fragment_specialist_translation);
        imageViewModelChange.set(R.mipmap.call_fragment_specialist_translation);
        textViewModelChange.set("语音呼叫");
        textViewColor1.set(Color.parseColor("#40d12d"));
        textViewColor2.set(Color.parseColor("#333333"));
        textViewTargetLanguage.set("英语");
    }

    public RelayCommand onRequestTranslation = new RelayCommand(() -> {
        // 0=图文 1=语音
        new TranslationOrderModel().generateOrder("0", mTranslationOrderType.getValue())
                .subscribe();
        onTranslationOrderGenerated.set(mTranslationOrderType);
        onTranslationOrderGenerated.notifyChange();
    });

    public void languagePickerLinearLayoutClick(View view) {//选取目标语言
        if (languagePickerDialogViewModel == null) {
            languagePickerDialogViewModel = new LanguagePickerDialogViewModel(view.getContext());
        }

        languagePickerDialogViewModel.setPickLanguage(language -> textViewTargetLanguage.set(language));
        languagePickerDialogViewModel.show();
    }

    public void modelChangeImageViewClick(View view) {//选择翻译模式，语言或者图文
        if (mTranslationOrderType == OrderType.MESSAGE) {
            modelChange.set(R.mipmap.radio_green_fragment_specialist_translation);
            imageViewModelChange.set(R.mipmap.call_fragment_specialist_translation);
            textViewModelChange.set("语音呼叫");
            textViewColor1.set(Color.parseColor("#40d12d"));
            textViewColor2.set(Color.parseColor("#333333"));
            mTranslationOrderType = OrderType.AUDIO;
        } else {
            modelChange.set(R.mipmap.radio_brown_fragment_specialist_translation);
            imageViewModelChange.set(R.mipmap.picture_fragment_specialist_translation);
            textViewModelChange.set("图文翻译");
            textViewColor1.set(Color.parseColor("#333333"));
            textViewColor2.set(Color.parseColor("#ffb900"));
            mTranslationOrderType = OrderType.MESSAGE;
        }
    }
}
