package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.lyun.library.BuildConfig;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

/**
 * @author Gordon
 * @since 2017/1/6
 * do()
 */

public class UserSettingViewModel extends ViewModel {

    public final ObservableField<String> appVersion = new ObservableField<>();

    public UserSettingViewModel() {
        init();
    }
    private void init() {
        appVersion.set(BuildConfig.VERSION_NAME);
    }

    @WatchThis
    public final BaseObservable onNavigationModifyPassword = new BaseObservable();

    public RelayCommand onModifyPasswordButtonClick = new RelayCommand(() -> {
        onNavigationModifyPassword.notifyChange();
    });

}
