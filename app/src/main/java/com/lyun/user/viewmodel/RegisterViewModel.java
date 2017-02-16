package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.model.RegisterModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ZHAOWEIWEI on 2017/1/16.
 */

public class RegisterViewModel extends ViewModel {

    public final ObservableField<String> username = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");
    public final ObservableField<String> confirmPassword = new ObservableField<>("");

    @WatchThis
    public final BaseObservable onRegisterSuccess = new BaseObservable();
    @WatchThis
    public final ObservableField<Throwable> onRegisterFailed = new ObservableField<>();
    @WatchThis
    public final BaseObservable onPasswordBlank = new BaseObservable();
    @WatchThis
    public final BaseObservable onConfirmPasswordBlank = new BaseObservable();
    @WatchThis
    public final BaseObservable onPasswordSame = new BaseObservable();

    public RelayCommand onRegisterButtonClick = new RelayCommand(() -> {
        if (("".equals(password.get())) || (null == password.get())) {
            onPasswordBlank.notifyChange();
        } else if (("".equals(confirmPassword.get())) || (null == confirmPassword.get())) {
            onConfirmPasswordBlank.notifyChange();
        } else if (!(password.get().equals(confirmPassword.get()))) {
            onPasswordSame.notifyChange();
        } else {
            register(username.get(), password.get());
        }

    });

    private void register(String username, String password) {
        new RegisterModel().register(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> onRegisterSuccess.notifyChange(),
                        throwable -> onRegisterFailed.set(throwable));
    }
}
