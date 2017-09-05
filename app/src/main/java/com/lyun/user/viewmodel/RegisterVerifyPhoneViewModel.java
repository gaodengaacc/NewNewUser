package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.lyun.api.exception.APINeedDealException;
import com.lyun.api.exception.APINotSuccessException;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.bindingadapter.edittext.ViewBindingAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.AppIntent;
import com.lyun.user.eventbusmessage.EventIntentActivityMessage;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.eventbusmessage.login.EventThirdPhoneIsRegisterMessage;
import com.lyun.user.model.CheckVerificationModel;
import com.lyun.user.model.RegisterVerifyPhoneModel;
import com.lyun.utils.RegExMatcherUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ZHAOWEIWEI on 2017/1/16.
 */

public class RegisterVerifyPhoneViewModel extends ViewModel {
    public final ObservableField<String> username = new ObservableField<>("");//手机号码
    public final ObservableField<String> smscode = new ObservableField<>("");//获取的验证码
    public final ObservableField<String> mSendSmsCode = new ObservableField<>("");//获取验证码倒计时
    public final ObservableField<Boolean> clickable = new ObservableField<>();//设置获取验证码是否可以点击
    public final ObservableInt clearVisible = new ObservableInt();
    public final ObservableInt smsVisible = new ObservableInt();
    private TimeCount timeCount = new TimeCount(60000, 1000);//设置获取验证码的倒计时
    public Intent intent;
    private Bundle bundle = new Bundle();
    private String register_status = "2";
    private String third_register_status = "3";
    private boolean canClick = false;
    private boolean isThird;
    private String openId;
    private String loginType;

//    @WatchThis
//    public final ObservableField<Intent> onVerifySuccess = new ObservableField<>();//验证成功
//    @WatchThis
//    public final ObservableBoolean progressDialogShow = new ObservableBoolean();
//    @WatchThis
//    public final BaseObservable onSuccess = new BaseObservable();
//    @WatchThis
//    public final ObservableField<String> onVerifyResult = new ObservableField();

    public RegisterVerifyPhoneViewModel(boolean isThird, String openId, String loginType) {
        mSendSmsCode.set("获取验证码");
        clickable.set(true);
        clearVisible.set(View.INVISIBLE);
        smsVisible.set(View.GONE);
        this.isThird = isThird;
        this.openId = openId;
        this.loginType = loginType;
    }

    public RelayCommand onClearButtonClick = new RelayCommand(() -> {
        username.set("");
    });

    public RelayCommand onGetSMSCodeButtonClick = new RelayCommand(() -> {
        if (!canClick) {
            if (("".equals(username.get()) || (username.get() == null))) {
                EventBus.getDefault().post(new EventToastMessage("请输入手机号"));
            } else if (!RegExMatcherUtils.isMobileNO(username.get())) {
                // 产品2017/03/24需求变更 bugfree ID：7810
                EventBus.getDefault().post(new EventToastMessage("请输入有效手机号"));
            } else {
                canClick = true;
                if (isThird)
                    getSmsCode(username.get(), third_register_status);
                else
                    getSmsCode(username.get(), register_status);

            }
        } else {
            return;
        }


    });

    /**
     * 获取验证码
     *
     * @param username
     */
    private void getSmsCode(String username, String status) {
        new RegisterVerifyPhoneModel().getSmsCode(username, status)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    if (apiResult.isSuccess()) {
                        timeCount.start();
                    } else {
                        canClick = false;
                    }
                    EventBus.getDefault().post(new EventToastMessage(apiResult.getDescribe()));
                });
    }

    public RelayCommand onNextButtonClick = new RelayCommand(() -> {
        if (("".equals(username.get()) || (username.get() == null))) {
            EventBus.getDefault().post(new EventToastMessage("请输入手机号"));
        } else if (!RegExMatcherUtils.isMobileNO(username.get())) {
            EventBus.getDefault().post(new EventToastMessage("请输入有效手机号"));
        } else if (("".equals(smscode.get()) || (smscode.get() == null))) {
            EventBus.getDefault().post(new EventToastMessage("请输入验证码"));
        } else {
            intent = new Intent(AppIntent.ACTION_REGISTER);
            bundle.putString("username", username.get());
            if (isThird) {
                bundle.putBoolean("isThird", true);
                bundle.putString("openId", openId);
                bundle.putString("loginType", loginType);
            }
            intent.putExtras(bundle);

            checkVerification(username.get(), smscode.get());
        }

    });
    public RelayCommand onClearClick = new RelayCommand(() -> {
        username.set("");
        clearVisible.set(View.INVISIBLE);
    });
    public RelayCommand<ViewBindingAdapter.TextChangeData> editTextCommand = new RelayCommand<ViewBindingAdapter.TextChangeData>((data) -> {
        if (data.text.length() > 0)
            clearVisible.set(View.VISIBLE);
        else
            clearVisible.set(View.INVISIBLE);
    });

    /**
     * 验证验证码
     *
     * @param username
     * @param smscode
     */
    private void checkVerification(String username, String smscode) {
        EventBus.getDefault().post(new EventProgressMessage(true));
        new CheckVerificationModel().checkVerification(username, smscode)
                .flatMap(apiResult -> Observable.create(observable -> {
                    if (apiResult.isSuccess()) {
                        observable.onNext(apiResult.getStatus());
                        observable.onComplete();
                    } else {
                        observable.onError(new APINotSuccessException(apiResult));
                    }
                }))
                .flatMap(result -> Observable.create(observable -> {
                    if (isThird) {
                        observable.onNext(isThird);
                        observable.onComplete();
                    } else {
                        observable.onError(new APINeedDealException(new APIResult("-1", "验证成功", null)));
                    }
                }))
                .flatMap(result -> new RegisterVerifyPhoneModel().isBind(username, openId, loginType))
                .flatMap(apiResult -> Observable.create(observable -> {
                    if (apiResult.isSuccess() || apiResult.getStatus().equals("7")) {  // 7:该用户不存在
                        observable.onNext(apiResult.isSuccess());
                        observable.onComplete();
                    } else {
                        observable.onError(new APINotSuccessException(apiResult));
                    }
                }))
                .map(result -> (Boolean) result)
                .subscribe(result -> {
                            EventBus.getDefault().post(new EventProgressMessage(false));
                            EventBus.getDefault().post(new EventThirdPhoneIsRegisterMessage(result));
                        }
                        , throwable -> {
                            if (throwable instanceof APINeedDealException) {
                                timeCount.cancel();
                                EventBus.getDefault().post(new EventIntentActivityMessage(intent));
                            }
                            EventBus.getDefault().post(new EventProgressMessage(false));
                            EventBus.getDefault().post(new EventToastMessage(throwable.getMessage()));
                        }
                );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timeCount.cancel();
    }

    class TimeCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            smsVisible.set(View.VISIBLE);
            mSendSmsCode.set(millisUntilFinished / 1000 + "S");
            clickable.set(false);
        }

        @Override
        public void onFinish() {
            smsVisible.set(View.GONE);
            mSendSmsCode.set("重新获取");
            clickable.set(true);
            canClick = false;
        }
    }

}
