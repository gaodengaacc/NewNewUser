package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.os.CountDownTimer;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
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

    @WatchThis
    public final BaseObservable onVerifySuccess = new BaseObservable();
    @WatchThis
    public final BaseObservable onNumberBlank = new BaseObservable();
    @WatchThis
    public final BaseObservable onNumberWrong = new BaseObservable();

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


    public RelayCommand onNextButtonClick = new RelayCommand(() -> {
        onVerifySuccess.notifyChange();
    });

    private void getSmsCode(String username) {
        new RegisterVerifyPhoneModel().getSmsCode(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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
