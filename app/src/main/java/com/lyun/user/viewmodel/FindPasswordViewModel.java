package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.model.FindPasswordModel;
import com.lyun.utils.Validator;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ZHAOWEIWEI on 2017/1/16.
 */

public class FindPasswordViewModel extends ViewModel {
    public final ObservableField<String> username = new ObservableField<>("");//手机号码
    public final ObservableField<String> smscode = new ObservableField<>("");//获取的验证码
    public final ObservableField<String> newPassword = new ObservableField<>("");//新密码
    public final ObservableField<String> mSendSmsCode = new ObservableField<>("");//获取验证码倒计时
    public final ObservableField<Boolean> clickable = new ObservableField<>();//设置获取验证码是否可以点击
    public final ObservableField<TransformationMethod> inputType = new ObservableField<>();//设置密码显隐
    public final ObservableField<RelayCommand<Boolean>> checkedChangeListener = new ObservableField<>();
    private TimeCount timeCount = new TimeCount(60000, 1000);//设置能够获取验证码倒计时

    @WatchThis
    public final BaseObservable onFindPasswordSuccess = new BaseObservable();//提交成功
    @WatchThis
    public final BaseObservable onNumberBlank = new BaseObservable();//号码为空
    @WatchThis
    public final BaseObservable onNumberWrong = new BaseObservable();//号码错误
    @WatchThis
    public final BaseObservable onSmsCodeBlank = new BaseObservable();//验证码为空
    @WatchThis
    public final BaseObservable onNewPasswordBlank = new BaseObservable();//新密码为空
    @WatchThis
    public final ObservableBoolean progressDialogShow = new ObservableBoolean();
    @WatchThis
    public final ObservableField<String> onFindPasswordResult = new ObservableField();


    public FindPasswordViewModel() {
        mSendSmsCode.set("获取验证码");
        clickable.set(true);
        checkedChangeListener.set(isChecked);
        inputType.set(PasswordTransformationMethod.getInstance());//密码隐藏
    }

    public RelayCommand<Boolean> isChecked = new RelayCommand<Boolean>((checked) -> {
        if (checked) {
            inputType.set(HideReturnsTransformationMethod.getInstance());//密码显示
        } else {
            inputType.set(PasswordTransformationMethod.getInstance());//密码隐藏
        }
    });

    public RelayCommand onSubmitClick = new RelayCommand(() -> {
        if (("".equals(username.get()) || (username.get() == null))) {
            onNumberBlank.notifyChange();
        } else if (!Validator.isMobileNO(username.get())) {
            onNumberWrong.notifyChange();
        } else if (("".equals(smscode.get()) || (smscode.get() == null))) {
            onSmsCodeBlank.notifyChange();
        } else if (("".equals(newPassword.get()) || (newPassword.get() == null))) {
            onNewPasswordBlank.notifyChange();
        } else {
            submit(username.get(), smscode.get(), newPassword.get());//提交新密码
        }

    });

    /**
     * 提交新密码
     *
     * @param username
     * @param smscode
     * @param newPassword
     */
    private void submit(String username, String smscode, String newPassword) {
        progressDialogShow.set(true);
        new FindPasswordModel().findPassword(username, smscode, newPassword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    progressDialogShow.set(false);
                    if ("0".equals(apiResult.getStatus())) {//验证成功
                        timeCount.cancel();
                        onFindPasswordSuccess.notifyChange();
                    } else {
                        onFindPasswordResult.set(apiResult.getDescribe());
                    }
                });
    }

    public RelayCommand onGetSMSCodeButtonClick = new RelayCommand(() -> {
        if (("".equals(username.get()) || (username.get() == null))) {
            onNumberBlank.notifyChange();
        } else if (!Validator.isMobileNO(username.get())) {
            onNumberWrong.notifyChange();
        } else {
            timeCount.start();
            getSmsCode(username.get());//获取验证码
        }
    });

    /**
     * 获取验证码
     *
     * @param username
     */
    private void getSmsCode(String username) {
        new FindPasswordModel().getSmsCode(username)
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
