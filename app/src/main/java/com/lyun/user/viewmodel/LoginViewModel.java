package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.model.LoginModel;
import com.lyun.utils.L;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public class LoginViewModel extends ViewModel {

    public final ObservableField<String> username = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");

    @WatchThis
    public final BaseObservable onNavigationRegister = new BaseObservable();
    @WatchThis
    public final BaseObservable onNavigationFindPassword = new BaseObservable();
    @WatchThis
    public final BaseObservable onLoginSuccess = new BaseObservable();

    public RelayCommand onLoginButtonClick = new RelayCommand(() -> {
        new LoginModel().login(username.get(), password.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponseAPIResult -> onLoginSuccess.notifyChange(), throwable -> {
                    onLoginSuccess.notifyChange();
                    L.e("tag", throwable);
                });
    });

    public RelayCommand onRegisterButtonClick = new RelayCommand(() -> {
        onNavigationRegister.notifyChange();
    });

    public RelayCommand onFindPasswordButtonClick = new RelayCommand(() -> {
        onNavigationFindPassword.notifyChange();
    });

}
