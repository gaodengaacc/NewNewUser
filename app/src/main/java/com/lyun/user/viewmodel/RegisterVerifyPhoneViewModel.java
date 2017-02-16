package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.model.RegisterVerifyPhoneModel;
import com.lyun.utils.Validator;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ZHAOWEIWEI on 2017/1/16.
 */

public class RegisterVerifyPhoneViewModel extends ViewModel {
    public final ObservableField<String> username = new ObservableField<>("");//手机号码
    public final ObservableField<String> smscode = new ObservableField<>("");//获取的验证码
    @WatchThis
    public final BaseObservable onVerifySuccess = new BaseObservable();
    @WatchThis
    public final BaseObservable onNumberBlank = new BaseObservable();
    @WatchThis
    public final BaseObservable onNumberWrong = new BaseObservable();

    public RelayCommand onGetSMSCodeButtonClick = new RelayCommand(() -> {
        if (("".equals(username.get()) || (username.get() == null))) {
            onNumberBlank.notifyChange();
        } else if (!Validator.isMobileNO(username.get())) {
            onNumberWrong.notifyChange();
        } else {
            getSmsCode(username.get());
        }
    });


    public RelayCommand onNextButtonClick = new RelayCommand(() -> {
        onVerifySuccess.notifyChange();
    });

    private void getSmsCode(String username) {
        new RegisterVerifyPhoneModel().getSmsCode(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
