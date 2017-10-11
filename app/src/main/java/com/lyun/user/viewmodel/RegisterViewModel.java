package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.view.View;

import com.lyun.library.mvvm.bindingadapter.edittext.ViewBindingAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.AppIntent;
import com.lyun.user.R;
import com.lyun.user.eventbusmessage.login.EventRegisterSuccessMessage;
import com.lyun.user.model.RegisterModel;
import com.lyun.utils.RegExMatcherUtils;

import net.funol.databinding.watchdog.annotations.WatchThis;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
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
    public ObservableInt clearVisible = new ObservableInt();
    public final ObservableInt clearVisible2 = new ObservableInt();
    public final ObservableInt clearVisible3 = new ObservableInt();

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
    private boolean isThird;
    private String openId;
    private String loginType;
    public RegisterViewModel(Bundle bundle) {
        this.bundle = bundle;
        username.set(bundle.getString("username"));
        isThird = bundle.getBoolean("isThird");
        openId = bundle.getString("openId");
        loginType = bundle.getString("loginType");
        clearVisible2.set(View.INVISIBLE);
        clearVisible3.set(View.INVISIBLE);
    }

    public RelayCommand onRegisterButtonClick = new RelayCommand(() -> {
        if (("".equals(password.get())) || (null == password.get())) {
            ObservableNotifier.alwaysNotify(onRegisterResult, "请输入密码");
        } else if (("".equals(confirmPassword.get())) || (null == confirmPassword.get())) {
            ObservableNotifier.alwaysNotify(onRegisterResult, "请确认密码");
        } else if (!(password.get().equals(confirmPassword.get()))) {
            ObservableNotifier.alwaysNotify(onRegisterResult, "两次输入密码不同,请重新输入");
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
        Observable.just(isThird)
                .flatMap(isThird -> {
                    if (isThird) return new RegisterModel().registerThird(username,openId,loginType,password);
                    else return new RegisterModel().register(username, password);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
//                            progressDialogShow.set(false);
                            if (apiResult.isSuccess()) {
                                onRegisterSuccess.notifyChange();
                                EventBus.getDefault().post(new EventRegisterSuccessMessage(username, password));
                            } else {
                                progressDialogShow.set(false);
                                ObservableNotifier.alwaysNotify(onRegisterResult, apiResult.getDescribe());
                            }
                        },
                        throwable -> ObservableNotifier.alwaysNotify(onRegisterFailed, throwable));
    }

    public void onClearClick(View view) {
        switch (view.getId()) {
            case R.id.clear_text2:
                clearVisible = clearVisible2;
                password.set("");
                break;
            case R.id.clear_text3:
                clearVisible = clearVisible3;
                confirmPassword.set("");
                break;
            default:
                break;
        }
        clearVisible.set(View.INVISIBLE);
    }

    ;
    public RelayCommand<ViewBindingAdapter.TextChangeData> editTextCommand = new RelayCommand<ViewBindingAdapter.TextChangeData>((data) -> {
        switch (data.viewId) {
            case R.id.edit_password:
                clearVisible = clearVisible2;
                break;
            case R.id.edit_confirmPassword:
                clearVisible = clearVisible3;
                break;
            default:
                break;
        }
        if (data.text.length() > 0)
            clearVisible.set(View.VISIBLE);
        else
            clearVisible.set(View.INVISIBLE);
    });
}
