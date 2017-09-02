package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.DialogViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.Constants;
import com.lyun.user.R;
import com.lyun.user.adapter.LanguageTextAdapter;
import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.utils.ACache;

import net.funol.databinding.watchdog.annotations.WatchThis;

import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;

/**
 * Created by 郑成裕 on 2017/1/11.
 */

public class LanguagePickerDialogViewModel extends DialogViewModel {

    public final ObservableField<OnWheelChangedListener> changedListener = new ObservableField<>();
    public final ObservableField<LanguageTextAdapter> adapter = new ObservableField<>();
    public final ObservableInt visibleItem = new ObservableInt();
    public final ObservableInt currentItem = new ObservableInt();
    public final ObservableField<Boolean> isCyclic = new ObservableField<>();
    public final ObservableInt foreground = new ObservableInt();

    @WatchThis
    public final ObservableField<FindLanguageResponse> onLanguagePicked = new ObservableField<>();

    public final ObservableField<List<FindLanguageResponse>> mLanguageDatas = new ObservableField<>();

    //已选择的语种
    private FindLanguageResponse mCurrentSelectedLanguage = null;

    private final String defaultLanguageCache = "[{\"id\":9,\"code\":\"110\",\"name\":\"民间借贷\",\"description\":\"民间借贷\"}]";

    public LanguagePickerDialogViewModel() {
        initData();
    }

    private void initData() {
        changedListener.set(onWheelChangedListener);
        // initListData();//设置选项值
        visibleItem.set(7);//设置item的显示数目
        currentItem.set(0);
        isCyclic.set(false);//设置循环
        foreground.set(R.mipmap.bg_wheel_divider);

        String languageStr = ACache.get(AppApplication.getInstance()).getAsString(Constants.Cache.SUPPORT_LANGUAGES);
        mLanguageDatas.set(new Gson().fromJson(languageStr == null ? defaultLanguageCache : languageStr, new TypeToken<List<FindLanguageResponse>>() {
        }.getType()));

        if (mLanguageDatas.get() != null && mLanguageDatas.get().size() > 0) {
            //设置默认为第一个
            currentItem.set(0);
            mCurrentSelectedLanguage = mLanguageDatas.get().get(currentItem.get());
            onLanguagePicked.set(mCurrentSelectedLanguage);
        }
    }

    private OnWheelChangedListener onWheelChangedListener = (wheel, oldValue, newValue) -> {
        mCurrentSelectedLanguage = mLanguageDatas.get().get(wheel.getCurrentItem());
    };

    public RelayCommand onDoneClickCommand = new RelayCommand(() -> {
        ObservableNotifier.alwaysNotify(onLanguagePicked, mCurrentSelectedLanguage);
    });

}
