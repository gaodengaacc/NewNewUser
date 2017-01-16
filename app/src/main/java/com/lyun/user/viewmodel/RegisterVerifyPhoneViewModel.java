package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

/**
 * Created by ZHAOWEIWEI on 2017/1/16.
 */

public class RegisterVerifyPhoneViewModel extends ViewModel {

    @WatchThis
    public final BaseObservable onVerifySuccess = new BaseObservable();

    public RelayCommand onNextButtonClick = new RelayCommand(() -> {
        onVerifySuccess.notifyChange();
    });

}
