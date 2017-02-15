package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.model.RegisterVerifyPhoneModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ZHAOWEIWEI on 2017/1/16.
 */

public class RegisterVerifyPhoneViewModel extends ViewModel {
    public final ObservableField<String> username = new ObservableField<>("");
    public final ObservableField<String> smscode = new ObservableField<>("");
    @WatchThis
    public final BaseObservable onVerifySuccess = new BaseObservable();

    public RelayCommand onGetSMSCodeButtonClick = new RelayCommand(() -> {
//        getSmsCode(username.get());
    });


    public RelayCommand onNextButtonClick = new RelayCommand(() -> {

        onVerifySuccess.notifyChange();
        getActivity().finish();
    });

    private void getSmsCode(String username) {
        new RegisterVerifyPhoneModel().getSmsCode(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
