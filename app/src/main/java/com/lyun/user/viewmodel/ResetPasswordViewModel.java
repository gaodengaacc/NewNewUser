package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.model.ResetPasswordModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;

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
    @WatchThis
    public final BaseObservable onNewPasswordBlank = new BaseObservable();//新密码为空
    @WatchThis
    public final BaseObservable onPasswordBlank = new BaseObservable();//原密码为空
    @WatchThis
    public final BaseObservable onConfirmPasswordBlank = new BaseObservable();//确认密码为空

    public RelayCommand onSubmitClick = new RelayCommand(() -> {
        if ("".equals(password.get())) {
            onPasswordBlank.notifyChange();
        } else if ("".equals(newPassword1.get())) {
            onNewPasswordBlank.notifyChange();
        } else if ("".equals(newPassword2.get())) {
            onConfirmPasswordBlank.notifyChange();
        } else if (!newPassword1.get().equals(newPassword2.get())) {
            onResetPasswordResult.set("两次新密码输入不同!");
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                            progressDialogShow.set(false);
                            if (apiResult.getStatus().equals("0")) {
                                onResetPasswordResult.set("修改成功,请重新登录!");
                                Account.preference().clear();
                                onLogout.notifyChange();
                            } else {
                                onResetPasswordResult.set(apiResult.getDescribe());
                            }
                        }
                        , throwable -> {
                            onResetPasswordResult.set(throwable.getMessage());
                            throwable.printStackTrace();
                        });
    }

}
