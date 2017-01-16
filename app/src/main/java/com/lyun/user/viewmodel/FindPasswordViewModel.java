package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

/**
 * Created by ZHAOWEIWEI on 2017/1/16.
 */

public class FindPasswordViewModel extends ViewModel {

    @WatchThis
    public final BaseObservable onFindPasswordSuccess = new BaseObservable();

    public RelayCommand onSubmitClick = new RelayCommand(() -> {
        onFindPasswordSuccess.notifyChange();
    });
}
