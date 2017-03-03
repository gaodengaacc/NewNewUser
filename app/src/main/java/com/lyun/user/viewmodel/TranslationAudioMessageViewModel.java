package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

/**
 * Created by ZHAOWEIWEI on 2017/3/3.
 */

public class TranslationAudioMessageViewModel extends ViewModel {

    public final ObservableField<String> targetLanguage = new ObservableField<>();
    public final ObservableField<String> translatorName = new ObservableField<>();
    public final ObservableField<String> translationTime = new ObservableField<>();
    public final ObservableField<String> translatorAvatar = new ObservableField<>();

    @WatchThis
    public final BaseObservable switchMessageMode = new BaseObservable();

    public final RelayCommand<Boolean> onMuteCheckCommand = new RelayCommand<>(isChecked -> {

    });

    public final RelayCommand<Boolean> onHangUpCheckCommand = new RelayCommand<>(isChecked -> {

    });

    public final RelayCommand<Boolean> onHandFreeCheckCommand = new RelayCommand<>(isChecked -> {

    });

    public final RelayCommand onSwitchClicked = new RelayCommand(() -> {
        switchMessageMode.notifyChange();
    });

}
