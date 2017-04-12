package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.AppIntent;
import com.lyun.user.model.CheckVerificationModel;
import com.lyun.user.model.RegisterVerifyPhoneModel;
import com.lyun.utils.RegExMatcherUtils;

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
    private String status = "2";
    private boolean canClick = false;

    @WatchThis
    public final ObservableField<Intent> onVerifySuccess = new ObservableField<>();//验证成功
    @WatchThis
    public final ObservableBoolean progressDialogShow = new ObservableBoolean();
    @WatchThis
    public final BaseObservable onSuccess = new BaseObservable();
    @WatchThis
    public final ObservableField<String> onVerifyResult = new ObservableField();

    public RegisterVerifyPhoneViewModel() {
        mSendSmsCode.set("获取验证码");
        clickable.set(true);

    }

    public RelayCommand onGetSMSCodeButtonClick = new RelayCommand(() -> {
        if (!canClick) {
            if (("".equals(username.get()) || (username.get() == null))) {
                ObservableNotifier.alwaysNotify(onVerifyResult, "请输入手机号");
            } else if (!RegExMatcherUtils.isMobileNO(username.get())) {
                // 产品2017/03/24需求变更 bugfree ID：7810
                ObservableNotifier.alwaysNotify(onVerifyResult, "请输入有效手机号");
            } else {
                canClick = true;
                getSmsCode(username.get(), status);
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
                        ObservableNotifier.alwaysNotify(onVerifyResult, apiResult.getDescribe());
                        timeCount.start();
                    } else {
                        ObservableNotifier.alwaysNotify(onVerifyResult, apiResult.getDescribe());
                        canClick = false;
                    }
                });
    }

    public RelayCommand onNextButtonClick = new RelayCommand(() -> {
        if (("".equals(username.get()) || (username.get() == null))) {
            ObservableNotifier.alwaysNotify(onVerifyResult, "请输入手机号");
        } else if (!RegExMatcherUtils.isMobileNO(username.get())) {
            ObservableNotifier.alwaysNotify(onVerifyResult, "请输入有效手机号");
        } else if (("".equals(smscode.get()) || (smscode.get() == null))) {
            ObservableNotifier.alwaysNotify(onVerifyResult, "请输入验证码");
        } else {
            intent = new Intent(AppIntent.ACTION_REGISTER);
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
        progressDialogShow.set(true);
        new CheckVerificationModel().checkVerification(username, smscode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    progressDialogShow.set(false);
                    if (apiResult.isSuccess()) {//验证成功
                        timeCount.cancel();
                        onVerifySuccess.set(intent);
                        onSuccess.notifyChange();
                    } else {
                        onVerifyResult.set(apiResult.getDescribe());
                    }
                });
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
            mSendSmsCode.set(millisUntilFinished / 1000 + "S");
            clickable.set(false);
        }

        @Override
        public void onFinish() {
            mSendSmsCode.set("重新获取");
            clickable.set(true);
            canClick = false;
        }
    }

}
