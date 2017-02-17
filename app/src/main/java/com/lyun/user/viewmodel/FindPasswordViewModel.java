package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.utils.Validator;

import net.funol.databinding.watchdog.annotations.WatchThis;

/**
 * Created by ZHAOWEIWEI on 2017/1/16.
 */

public class FindPasswordViewModel extends ViewModel {
    public final ObservableField<String> username = new ObservableField<>("");//手机号码
    public final ObservableField<String> smscode = new ObservableField<>("");//获取的验证码
    public final ObservableField<String> newPassword = new ObservableField<>("");//新密码
    public final ObservableField<String> mSendSmsCode = new ObservableField<>("");//获取验证码倒计时
    public final ObservableInt isVisible = new ObservableInt();
    public final ObservableField<Boolean> clickable = new ObservableField<>();
    public final ObservableField<TransformationMethod> inputType = new ObservableField<>();
    private TimeCount timeCount = new TimeCount(60000, 1000);
    private Boolean aBoolean = false;
    @WatchThis
    public final BaseObservable onFindPasswordSuccess = new BaseObservable();
    @WatchThis
    public final BaseObservable onNumberBlank = new BaseObservable();
    @WatchThis
    public final BaseObservable onNumberWrong = new BaseObservable();

    public FindPasswordViewModel() {
        mSendSmsCode.set("获取验证码");
        clickable.set(true);
        isVisible.set(R.mipmap.ic_password_invisible);
        inputType.set(PasswordTransformationMethod.getInstance());//密码隐藏
    }

    public RelayCommand onSubmitClick = new RelayCommand(() -> {
        onFindPasswordSuccess.notifyChange();
    });
    public RelayCommand onVisible = new RelayCommand(() -> {
        if (!aBoolean) {
            isVisible.set(R.mipmap.ic_password_visible);
            inputType.set(HideReturnsTransformationMethod.getInstance());//密码显示
            aBoolean = true;
        } else {
            isVisible.set(R.mipmap.ic_password_invisible);
            inputType.set(PasswordTransformationMethod.getInstance());//密码隐藏
            aBoolean = false;
        }

    });
    public RelayCommand onGetSMSCodeButtonClick = new RelayCommand(() -> {
        if (("".equals(username.get()) || (username.get() == null))) {
            onNumberBlank.notifyChange();
        } else if (!Validator.isMobileNO(username.get())) {
            onNumberWrong.notifyChange();
        } else {
            timeCount.start();
        }
    });

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
