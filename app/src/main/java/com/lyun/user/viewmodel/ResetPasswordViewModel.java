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
import com.lyun.user.model.LoginModel;
import com.lyun.user.model.ResetPasswordModel;
import com.lyun.utils.RegExMatcherUtils;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Gordon
 * @since 2017/2/15
 * do()
 */

public class ResetPasswordViewModel extends ViewModel {
    public final ObservableField<String> password = new ObservableField<>("");
    public final ObservableField<String> newPassword1 = new ObservableField<>("");
    public final ObservableField<String> newPassword2 = new ObservableField<>("");

    @WatchThis
    public final ObservableField<String> onResetPasswordResult = new ObservableField();
    @WatchThis
    public final ObservableBoolean progressDialogShow = new ObservableBoolean();
    @WatchThis
    public final BaseObservable onLogout = new BaseObservable();

    public RelayCommand onSubmitClick = new RelayCommand(() -> {
        if ("".equals(password.get())) {
            ObservableNotifier.alwaysNotify(onResetPasswordResult, "请输入原密码!");
        } else if ("".equals(newPassword1.get())) {
            ObservableNotifier.alwaysNotify(onResetPasswordResult, "请输入新密码!");
        } else if ("".equals(newPassword2.get())) {
            ObservableNotifier.alwaysNotify(onResetPasswordResult, "请确认新密码!");
        } else if (!newPassword1.get().equals(newPassword2.get())) {
            ObservableNotifier.alwaysNotify(onResetPasswordResult, "两次新密码输入不同!");
        } else if (!RegExMatcherUtils.matchPassword(newPassword1.get())) {
            ObservableNotifier.alwaysNotify(onResetPasswordResult, "新密码格式不正确,请重新输入!");
        } else {
            resetPassword(Account.preference().getPhone(), password.get(), newPassword1.get());
        }
    });
//    public RelayCommand<Boolean> onOldCheckedCommand = new RelayCommand<Boolean>((isChecked) -> {
//        if (isChecked) {
//            oldMethod.set(HideReturnsTransformationMethod.getInstance());
//        } else {
//            oldMethod.set(PasswordTransformationMethod.getInstance());
//        }
//    });
//    public RelayCommand<Boolean> onNewCheckedCommand = new RelayCommand<Boolean>((isChecked) -> {
//        if (isChecked) {
//            newMethod.set(HideReturnsTransformationMethod.getInstance());
//        } else {
//            newMethod.set(PasswordTransformationMethod.getInstance());
//        }
//    });

    private void resetPassword(String userName, String password, String newPassword) {
        progressDialogShow.set(true);
        new ResetPasswordModel().resetPassword(userName, password, newPassword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                            if (apiResult.getStatus().equals("0")) {
                                Account.preference().clear();
                                login(userName, newPassword);
                                onResetPasswordResult.set("修改成功!");
                            } else {
                                onResetPasswordResult.set(apiResult.getDescribe());
                                progressDialogShow.set(false);
                            }
                        }
                        , throwable -> {
                            onResetPasswordResult.set(throwable.getMessage());
                            throwable.printStackTrace();
                            progressDialogShow.set(false);
                        });
    }

    private void login(String username, String password) {
        new LoginModel().login(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                            loginNim(username, password, loginResponse);
                        },
                        throwable -> {
                            progressDialogShow.set(false);
                        });
    }

    private void loginNim(String username, String password, LoginResponse loginResponse) {
        NimLoginHelper.login(username, loginResponse.getYunXinToken()).subscribe(
                loginInfo -> {
                    progressDialogShow.set(false);
                    Account.preference().savePhone(username);
                    Account.preference().savePassword(password);
                    Account.preference().saveToken(loginResponse.getAppToken());
                    Account.preference().saveNimToken(loginResponse.getYunXinToken());
                    Account.preference().setLogin(true);
                    onLogout.notifyChange();
                },
                throwable -> {
                    progressDialogShow.set(false);
                });
    }
}
