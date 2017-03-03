package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.api.response.LoginResponse;
import com.lyun.user.im.login.NimLoginHelper;
import com.lyun.user.model.LanguageModel;
import com.lyun.user.model.LoginModel;
import com.lyun.utils.RegExMatcherUtils;

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
    @WatchThis
    public final ObservableField<String> onLoginResult = new ObservableField<>();
    @WatchThis
    public final ObservableBoolean progressDialogShow = new ObservableBoolean();

    public RelayCommand onLoginButtonClick = new RelayCommand(() -> {
        if (("".equals(username.get()) || (username.get() == null))) {
            ObservableNotifier.alwaysNotify(onLoginResult, "请输入手机号!");
        } else if (!RegExMatcherUtils.isMobileNO(username.get())) {
            ObservableNotifier.alwaysNotify(onLoginResult, "错误手机号!");
        } else if (("".equals(password.get())) || (null == password.get())) {
            ObservableNotifier.alwaysNotify(onLoginResult, "请输入密码!");
        } else if (!RegExMatcherUtils.matchPassword(password.get())) {
            ObservableNotifier.alwaysNotify(onLoginResult, "密码格式不正确,请重新输入!");
        } else {
            login(username.get(), password.get());
        }

    });

    public RelayCommand onRegisterButtonClick = new RelayCommand(() -> {
        onNavigationRegister.notifyChange();
    });

    public RelayCommand onFindPasswordButtonClick = new RelayCommand(() -> {
        onNavigationFindPassword.notifyChange();
    });

    private void login(String username, String password) {
        progressDialogShow.set(true);
        new LoginModel().login(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                            loginNim(username, password, loginResponse);
                            progressDialogShow.set(false);
                        },
                        throwable -> {
                            onLoginFailed.set(throwable);
                            progressDialogShow.set(false);
                        });
    }

    private void loginNim(String username, String password, LoginResponse loginResponse) {
        NimLoginHelper.login(username, loginResponse.getYunXinToken()).subscribe(
                loginInfo -> {
                    Account.preference().savePhone(username);
                    Account.preference().savePassword(password);
                    Account.preference().saveToken(loginResponse.getAppToken());
                    Account.preference().saveNimToken(loginResponse.getYunXinToken());
                    Account.preference().setLogin(true);
                    onLoginSuccess.notifyChange();
                    getFindByLanguage();
                },
                throwable -> onLoginFailed.set(throwable));
    }

    private void getFindByLanguage() {
        new LanguageModel().updateLanguages();
    }

}
