package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.AppIntent;
import com.lyun.user.model.RegisterModel;
import com.lyun.utils.RegExMatcherUtils;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ZHAOWEIWEI on 2017/1/16.
 */

public class RegisterViewModel extends ViewModel {

    public final ObservableField<String> username = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");
    public final ObservableField<String> confirmPassword = new ObservableField<>("");
    private Intent intent;
    private Bundle bundle = new Bundle();
    private Bundle bundle1 = new Bundle();

    @WatchThis
    public final BaseObservable onRegisterSuccess = new BaseObservable();//注册成功
    @WatchThis
    public final ObservableField<Throwable> onRegisterFailed = new ObservableField<>();//
    @WatchThis
    public final ObservableBoolean progressDialogShow = new ObservableBoolean();
    @WatchThis
    public final ObservableField<String> onRegisterResult = new ObservableField();
    @WatchThis
    public final ObservableField<Intent> onAgreementResult = new ObservableField();

    public RegisterViewModel(Bundle bundle) {
        this.bundle = bundle;
        username.set(bundle.getString("username"));
    }

    public RelayCommand onRegisterButtonClick = new RelayCommand(() -> {
        if (("".equals(password.get())) || (null == password.get())) {
            ObservableNotifier.alwaysNotify(onRegisterResult, "请输入密码!");
        } else if (("".equals(confirmPassword.get())) || (null == confirmPassword.get())) {
            ObservableNotifier.alwaysNotify(onRegisterResult, "请确认密码!");
        } else if (!(password.get().equals(confirmPassword.get()))) {
            ObservableNotifier.alwaysNotify(onRegisterResult, "两次输入密码不同,请重新输入!");
        } else if (!RegExMatcherUtils.matchPassword(password.get())) {
            ObservableNotifier.alwaysNotify(onRegisterResult, "请正确输入6~16位字母或数字");
        } else {
            register(username.get(), password.get());
        }
    });

    public RelayCommand onAgreement = new RelayCommand(() -> {
        intent = new Intent(AppIntent.ACTION_AGREEMENT);
        bundle1.putString("agreementType", "register");
        intent.putExtras(bundle1);
        onAgreementResult.set(intent);
    });

    /**
     * 注册
     *
     * @param username
     * @param password
     */
    private void register(String username, String password) {
        progressDialogShow.set(true);
        new RegisterModel().register(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                            progressDialogShow.set(false);
                            if (apiResult.isSuccess()) {
                                onRegisterSuccess.notifyChange();
                            } else {
                                onRegisterResult.set(apiResult.getDescribe());
                            }
                        },
                        throwable -> onRegisterFailed.set(throwable));
    }
}
