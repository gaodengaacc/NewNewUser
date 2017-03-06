package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
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
    @WatchThis
    public final ObservableBoolean muteLocalAudio = new ObservableBoolean();
    @WatchThis
    public final ObservableBoolean hangUpAudioCall = new ObservableBoolean();
    @WatchThis
    public final ObservableBoolean handFreeMode = new ObservableBoolean();

    public final RelayCommand<Boolean> onMuteCheckCommand = new RelayCommand<>(isChecked -> ObservableNotifier.alwaysNotify(muteLocalAudio, isChecked));

    public final RelayCommand<Boolean> onHangUpCheckCommand = new RelayCommand<>(isChecked -> ObservableNotifier.alwaysNotify(hangUpAudioCall, isChecked));

    public final RelayCommand<Boolean> onHandFreeCheckCommand = new RelayCommand<>(isChecked -> ObservableNotifier.alwaysNotify(handFreeMode, isChecked));

    public final RelayCommand onSwitchClicked = new RelayCommand(() -> {
        switchMessageMode.notifyChange();
    });

}
