package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.api.response.LoginResponse;
import com.lyun.user.im.login.NimLoginHelper;
import com.lyun.user.model.LoginModel;
import com.lyun.user.model.HomeFragmentModel;

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
    @WatchThis
    public final ObservableField<Throwable> onLoginFailed = new ObservableField<>();

    public RelayCommand onLoginButtonClick = new RelayCommand(() -> {
        login(username.get(), password.get());
    });

    public RelayCommand onRegisterButtonClick = new RelayCommand(() -> {
        onNavigationRegister.notifyChange();
    });

    public RelayCommand onFindPasswordButtonClick = new RelayCommand(() -> {
        onNavigationFindPassword.notifyChange();
    });

    private void login(String username, String password) {
        new LoginModel().login(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponseAPIResult -> loginNim(username, password, loginResponseAPIResult.getContent()),
                        throwable -> onLoginFailed.set(throwable));
    }

    private void loginNim(String username, String password, LoginResponse loginResponse) {
        NimLoginHelper.login(username, loginResponse.getYunXinToken()).subscribe(
                loginInfo -> {
                    Account.preference().savePhone(username);
                    Account.preference().savePassword(password);
                    Account.preference().saveToken(loginResponse.getAppKey());
                    Account.preference().setLogin(true);
                    onLoginSuccess.notifyChange();
                    getFindByLanguage();
                },
                throwable -> onLoginFailed.set(throwable));
    }

    private void getFindByLanguage() {
        new HomeFragmentModel().setFindByLanguage();
    }

}
