package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.model.TranslationOrderModel;

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
        status.set("译员正在赶来，请稍候...");
        countDownTime.set(60);
        mCountDownTimer.schedule(mCountDownTimerTask, 1000, 1000);
    }

    public void stopTimer(){
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

    public RelayCommand<Boolean> onMuteCheckCommand = new RelayCommand<>(isChecked -> {

    });

    public RelayCommand<Boolean> onHangUpCheckCommand = new RelayCommand<>(isChecked -> {
        cancelOrder();
        ObservableNotifier.alwaysNotify(onOrderCanceled, null);
    });

    public RelayCommand<Boolean> onHandFreeCheckCommand = new RelayCommand<>(isChecked -> {

    });

    private int cancelOrderRetryCount = 0;

    private void cancelOrder() {
        new TranslationOrderModel().cancelOrder(userOrderId)
                .subscribe(result -> {

                        },
                        throwable -> {
                            if (cancelOrderRetryCount < 3) {
                                cancelOrderOnTimerOut();
                                cancelOrderRetryCount++;
                            }
                        });
    }

    private void cancelOrderOnTimerOut() {
        cancelOrder();
        ObservableNotifier.alwaysNotify(onOrderCanceled, "译员正忙，请稍后");
    }
}
