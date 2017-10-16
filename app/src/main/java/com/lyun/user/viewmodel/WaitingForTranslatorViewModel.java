package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.google.gson.Gson;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.model.TranslationOrderModel;
import com.lyun.utils.L;

import net.funol.databinding.watchdog.annotations.WatchThis;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZHAOWEIWEI on 2017/3/1.
 */

public class WaitingForTranslatorViewModel extends ViewModel {

    public final ObservableField<String> status = new ObservableField<>();
    public final ObservableInt countDownTime = new ObservableInt();

    private String userOrderId = "";

    @WatchThis
    public final ObservableField<String> onOrderCanceled = new ObservableField<>();

    public WaitingForTranslatorViewModel(String userOrderId) {
        this.userOrderId = userOrderId;
        status.set("律师正在赶来，请稍候...");
        countDownTime.set(60);
        mCountDownTimer.schedule(mCountDownTimerTask, 1000, 1000);
    }

    public void stopTimer() {
        mCountDownTimer.cancel();
    }

    private Timer mCountDownTimer = new Timer();
    private TimerTask mCountDownTimerTask = new TimerTask() {
        @Override
        public void run() {
            countDownTime.set(countDownTime.get() - 1);
            if (countDownTime.get() == 0) {
                mCountDownTimer.cancel();
                cancelOrderOnTimerOut();
            }
        }
    };

    public boolean muteMode = false;

    public RelayCommand<Boolean> onMuteCheckCommand = new RelayCommand<>(isChecked -> muteMode = isChecked);

    public boolean canHangup = true;

    public RelayCommand<Boolean> onHangUpCheckCommand = new RelayCommand<>(isChecked -> {
        L.d("AVChat", "点击挂断 -> canHangup:" + canHangup);
        synchronized (WaitingForTranslatorViewModel.this) {
            L.d("AVChat", "执行取消订单 -> canHangup:" + canHangup);
            if (canHangup) {
                cancelOrder();
                ObservableNotifier.alwaysNotify(onOrderCanceled, null);
            }
        }
    });

    public boolean handFreeMode = false;

    public RelayCommand<Boolean> onHandFreeCheckCommand = new RelayCommand<>(isChecked -> handFreeMode = isChecked);

    private void cancelOrder() {
        stopTimer();
        new TranslationOrderModel().cancelOrder(userOrderId)
                .subscribe(result -> {

                }, throwable -> {

                });
    }

    private void cancelOrderOnTimerOut() {
        cancelOrder();
        ObservableNotifier.alwaysNotify(onOrderCanceled, "律师正忙，请稍后");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCountDownTimer.cancel();
    }
}
