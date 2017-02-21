package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.model.CheckVerificationModel;
import com.lyun.user.model.RegisterVerifyPhoneModel;
import com.lyun.utils.Validator;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ZHAOWEIWEI on 2017/1/16.
 */

public class RegisterVerifyPhoneViewModel extends ViewModel {
    public final ObservableField<String> username = new ObservableField<>("");//手机号码
    public final ObservableField<String> smscode = new ObservableField<>("");//获取的验证码
    public final ObservableField<String> mSendSmsCode = new ObservableField<>("");//获取验证码倒计时
    public final ObservableField<Boolean> clickable = new ObservableField<>();//设置获取验证码是否可以点击
    private TimeCount timeCount = new TimeCount(60000, 1000);//设置获取验证码的倒计时
    private Intent intent;
    private Bundle bundle = new Bundle();

    @WatchThis
    public final ObservableField<Intent> onVerifySuccess = new ObservableField<>();//验证成功
    @WatchThis
    public final BaseObservable onNumberBlank = new BaseObservable();//号码为空
    @WatchThis
    public final BaseObservable onNumberWrong = new BaseObservable();//号码错误
    @WatchThis
    public final BaseObservable onSmsCodeBlank = new BaseObservable();//验证码为空
    @WatchThis
    public final BaseObservable onSmsCodeWrong = new BaseObservable();//验证码错误
    @WatchThis
    public final BaseObservable onSmsCodeExpired = new BaseObservable();//验证码过期

    public RegisterVerifyPhoneViewModel() {
        mSendSmsCode.set("获取验证码");
        clickable.set(true);

    }

    public RelayCommand onGetSMSCodeButtonClick = new RelayCommand(() -> {
        if (("".equals(username.get()) || (username.get() == null))) {
            onNumberBlank.notifyChange();
        } else if (!Validator.isMobileNO(username.get())) {
            onNumberWrong.notifyChange();
        } else {
            timeCount.start();
            getSmsCode(username.get());
        }
    });

    /**
     * 获取验证码
     *
     * @param username
     */
    private void getSmsCode(String username) {
        new RegisterVerifyPhoneModel().getSmsCode(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public RelayCommand onNextButtonClick = new RelayCommand(() -> {
        if (("".equals(username.get()) || (username.get() == null))) {
            onNumberBlank.notifyChange();
        } else if (!Validator.isMobileNO(username.get())) {
            onNumberWrong.notifyChange();
        } else if (("".equals(smscode.get()) || (smscode.get() == null))) {
            onSmsCodeBlank.notifyChange();
        } else {
            intent = new Intent("com.lyun.user.intent.action.REGISTER");
            bundle.putString("username", username.get());
            intent.putExtras(bundle);

            checkVerification(username.get(), smscode.get());
        }

    });

    /**
     * 验证验证码
     *
     * @param username
     * @param smscode
     */
    private void checkVerification(String username, String smscode) {
        new CheckVerificationModel().checkVerification(username, smscode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    if ("0".equals(apiResult.getStatus())) {//验证成功
                        onVerifySuccess.set(intent);
                    } else if ("1".equals(apiResult.getStatus())) {//验证码错误
                        onSmsCodeWrong.notifyChange();
                    } else if ("3002".equals(apiResult.getStatus())) {//验证码已过期
                        onSmsCodeExpired.notifyChange();
                    }
                });
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
            mSendSmsCode.set(millisUntilFinished / 1000 + "S");
            clickable.set(false);
        }

        @Override
        public void onFinish() {
            mSendSmsCode.set("获取验证码");
            clickable.set(true);
        }
    }
}
