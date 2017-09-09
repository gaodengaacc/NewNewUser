package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.api.exception.APINeedDealException;
import com.lyun.api.exception.APINotSuccessException;
import com.lyun.library.mvvm.bindingadapter.edittext.ViewBindingAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.BuildConfig;
import com.lyun.user.R;
import com.lyun.user.activity.LoginActivity;
import com.lyun.user.api.response.LoginResponse;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.login.EventCheckIsBindMessage;
import com.lyun.user.eventbusmessage.login.EventLoginSuccessMessage;
import com.lyun.user.eventbusmessage.login.EventQqLoginMessage;
import com.lyun.user.eventbusmessage.login.EventWbLoginMessage;
import com.lyun.user.eventbusmessage.login.EventWxLoginMessage;
import com.lyun.user.im.login.NimLoginHelper;
import com.lyun.user.model.LanguageModel;
import com.lyun.user.model.LoginModel;
import com.lyun.utils.RegExMatcherUtils;

import net.funol.databinding.watchdog.annotations.WatchThis;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public class LoginViewModel extends ViewModel {

    public final ObservableField<String> username = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");
    public final ObservableInt clearVisible1 = new ObservableInt();
    public final ObservableInt clearVisible2 = new ObservableInt();
    public ObservableInt clearVisible = new ObservableInt();


    @WatchThis
    public final BaseObservable onNavigationRegister = new BaseObservable();
    @WatchThis
    public final BaseObservable onNavigationFindPassword = new BaseObservable();
    @WatchThis
    public final ObservableField<Throwable> onLoginFailed = new ObservableField<>();
    @WatchThis
    public final ObservableField<String> onLoginResult = new ObservableField<>();
    @WatchThis
    public final ObservableBoolean progressDialogShow = new ObservableBoolean();

    public LoginViewModel() {
        init();
    }

    private String appToken;
    private String yunToken;
    private String yunCardNo;

    private void init() {
        clearVisible1.set(View.INVISIBLE);
        clearVisible2.set(View.INVISIBLE);
    }

    public RelayCommand onLoginButtonClick = new RelayCommand(() -> {
        if (("".equals(username.get()) || (username.get() == null))) {
            ObservableNotifier.alwaysNotify(onLoginResult, "请输入手机号");
        } else if (!RegExMatcherUtils.isMobileNO(username.get())) {
            ObservableNotifier.alwaysNotify(onLoginResult, "请输入有效手机号");
        } else if (("".equals(password.get())) || (null == password.get())) {
            ObservableNotifier.alwaysNotify(onLoginResult, "请输入密码");
        } else if (!RegExMatcherUtils.matchPassword(password.get())) {
            ObservableNotifier.alwaysNotify(onLoginResult, "密码格式不正确,请重新输入");
        } else {
            login(false, username.get(), password.get());
        }

    });

    public RelayCommand onRegisterButtonClick = new RelayCommand(() -> {
        onNavigationRegister.notifyChange();
    });

    public RelayCommand onFindPasswordButtonClick = new RelayCommand(() -> {
        onNavigationFindPassword.notifyChange();
    });

    public void login(Boolean isThirdLogin, String openId, String thirdType) {
        EventBus.getDefault().post(new EventProgressMessage(true));
        Observable.just(isThirdLogin)
                .flatMap(isThird -> {
                    if (isThird)
                        return new LoginModel().thirdLogin(openId, thirdType);
                    else
                        return new LoginModel().login(openId, thirdType);
                })
                .flatMap(result -> Observable.create((ObservableOnSubscribe<LoginResponse>)  observable -> {
                    if (result.isSuccess()) {
                        observable.onNext(result.getContent());
                        observable.onComplete();
                    } else if (result.getStatus().equals("6")) {//未绑定用户
                        observable.onError(new APINeedDealException(result));
                    } else {
                        observable.onError(new APINotSuccessException(result));
                    }
                }))
                .map(result -> {
                    Account.preference().setUserId(result.getUserId());
                    Account.preference().savePhone(result.getUserId());
                    Account.preference().setCardNo(result.getCardNo());
                    Account.preference().setUserHeader(result.getUserImg());
                    Account.preference().saveToken(result.getAppToken());
                    Account.preference().saveNimToken(result.getYunXinToken());
                    Account.preference().savePassword(password.get());
                    yunCardNo = result.getCardNo();
                    return result.getYunXinToken();
                })
                .subscribeOn(Schedulers.newThread())
                .flatMap(yunXinToken -> NimLoginHelper.login(yunCardNo, yunXinToken))
                .map(loginInfo -> {
                    Account.preference().setLogin(true);
                    return !Account.preference().isFirstLanguage();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isFirst -> {
                            if (isFirst) {
                                Account.preference().setFirstLanguage(true);
                            } else {
                                EventBus.getDefault().post(new EventProgressMessage(false));
                                EventBus.getDefault().post(new EventLoginSuccessMessage());
                            }
                            new LanguageModel().updateLanguages(isFirst);
                        },
                        throwable -> {
                            EventBus.getDefault().post(new EventProgressMessage(false));
                            if (throwable instanceof APINeedDealException)
                                EventBus.getDefault().post(new EventCheckIsBindMessage(new EventCheckIsBindMessage.UnBindMessage(openId, thirdType)));
                            else
                                onLoginFailed.set(throwable);
                        }
                );
    }

    public RelayCommand<ViewBindingAdapter.TextChangeData> editTextCommand = new RelayCommand<ViewBindingAdapter.TextChangeData>((data) -> {
        switch (data.viewId) {
            case R.id.edit_username:
                clearVisible = clearVisible1;
                break;
            case R.id.edit_password:
                clearVisible = clearVisible2;
                break;
            default:
                break;
        }
        if (data.text.length() > 0)
            clearVisible.set(View.VISIBLE);
        else
            clearVisible.set(View.INVISIBLE);
    });

    public void onClearClick(View view) {
        switch (view.getId()) {
            case R.id.clear_text1:
                username.set("");
                break;
            case R.id.clear_text2:
                password.set("");
                break;
            default:
                break;
        }
    }

    public boolean clickFlag;

    public void onThirdLogin(View view) {
        if (clickFlag || isFastDoubleClick()) return;
        EventBus.getDefault().post(new EventProgressMessage(true));
        switch (view.getId()) {
            case R.id.third_login_wx:
                EventBus.getDefault().post(new EventWxLoginMessage());
                break;
            case R.id.third_login_wb:
                EventBus.getDefault().post(new EventWbLoginMessage());
                break;
            case R.id.third_login_qq:
                EventBus.getDefault().post(new EventQqLoginMessage());
                break;
            default:
                break;
        }
        clickFlag = true;
    }

    //根据微信code 获取微信openId
    public void getWxOpenId(String code) {
        new LoginModel().getWxOpenId(BuildConfig.WX_PAY_APPID, "5de0ead7e50b32a44e85ba30308b898e", code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wxOpenIdResponse -> {
                    String openid = wxOpenIdResponse.getOpenid();
                    login(true, openid, LoginActivity.THIRD_WX);
                }, throwable -> onLoginFailed.set(throwable));
    }

    private long lastClickTime;

    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
