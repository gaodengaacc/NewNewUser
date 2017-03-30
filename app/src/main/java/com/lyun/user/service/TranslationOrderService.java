package com.lyun.user.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lyun.user.BuildConfig;
import com.lyun.user.model.TranslationOrderModel;
import com.lyun.utils.L;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class TranslationOrderService extends Service {

    private final String TAG = getClass().getSimpleName();

    private static TranslationOrderService mInstance;

    //心跳包时间间隔 s
    public final int HEART_BEAT_INTERVAL = 19;

    private TranslationOrder mTranslationOrder;

    public TranslationOrderService() {
        mTimer = new Timer();
    }

    public static boolean isRunning() {
        return mInstance == null ? false : true;
    }

    public static void start(Context context, String orderId, String targetLanguage,
                             TranslationOrderModel.OrderType orderType, String translatorId, String userId) {
        Intent intent = new Intent(context, TranslationOrderService.class);
        intent.putExtra(TranslationOrder.ORDER_ID, orderId);
        intent.putExtra(TranslationOrder.TARGET_LANGUAGE, targetLanguage);
        intent.putExtra(TranslationOrder.ORDER_TYPE, orderType);
        intent.putExtra(TranslationOrder.TRANSLATOR_ID, translatorId);
        intent.putExtra(TranslationOrder.USER_ID, userId);
        context.startService(intent);
    }

    public static void stop(Context context, int byWho, String reason) {
        Intent intent = new Intent();
        intent.putExtra(TranslationOrder.FINISH_REASON, reason);
        intent.putExtra(TranslationOrder.WHO_FINISH, byWho);
        intent.setAction(Action.COMMAND_STOP_SERVICE);
        context.sendBroadcast(intent);
    }

    protected BroadcastReceiver mStopServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            unregisterReceiver(this);

            mTranslationOrder.setFinishReason(intent.getStringExtra(TranslationOrder.FINISH_REASON));
            mTranslationOrder.setWhoFinish(intent.getIntExtra(TranslationOrder.WHO_FINISH, TranslationOrder.OTHER));
            L.i(TAG, "翻译服务关闭：reason -> " + mTranslationOrder.getFinishReason());
            stopSelf();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String orderId = intent.getStringExtra(TranslationOrder.ORDER_ID);
        TranslationOrderModel.OrderType orderType = (TranslationOrderModel.OrderType) intent.getSerializableExtra(TranslationOrder.ORDER_TYPE);
        String targetLanguage = intent.getStringExtra(TranslationOrder.TARGET_LANGUAGE);
        String userId = intent.getStringExtra(TranslationOrder.USER_ID);
        String translatorId = intent.getStringExtra(TranslationOrder.TRANSLATOR_ID);
        startNewOrder(new TranslationOrder(orderId, orderType, targetLanguage, System.currentTimeMillis(), userId, translatorId));

        IntentFilter intentFilter = new IntentFilter(Action.COMMAND_STOP_SERVICE);
        registerReceiver(mStopServiceReceiver, intentFilter);

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mInstance = null;

        mTimer.cancel();

        stopTranslation();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void startNewOrder(TranslationOrder translationOrder) {

        mTranslationOrder = translationOrder;

        startTranslation();
    }

    /**
     * 翻译服务开始
     */
    protected void startTranslation() {

        mTimer.schedule(mOrderTimerTask, 1000, 1000);

        setTranslationState(mTranslationOrder.getOrderId(), "0");

        Intent intent = new Intent();
        //设置intent的动作为，可以任意定义
        intent.setAction(Action.START);
        intent.putExtra(TranslationOrder.ORDER_ID, mTranslationOrder.getOrderId());
        intent.putExtra(TranslationOrder.TRANSLATOR_ID, mTranslationOrder.getTranslatorId());
        intent.putExtra(TranslationOrder.USER_ID, mTranslationOrder.getUserId());
        //发送无序广播
        sendBroadcast(intent);

        L.i(TAG, "开始翻译服务：" + new Gson().toJson(mTranslationOrder));
    }

    /**
     * 更新翻译进度
     */
    protected void processTranslation() {
        mTranslationOrder.setServicedTime(System.currentTimeMillis() - mTranslationOrder.getStartTime());

        Intent intent = new Intent();
        //设置intent的动作为，可以任意定义
        intent.setAction(Action.STATUS_CHANGE);
        intent.putExtra(TranslationOrder.ORDER_ID, mTranslationOrder.getOrderId());
        intent.putExtra(TranslationOrder.SERVICED_TIME, mTranslationOrder.getServicedTime());
        //发送无序广播
        sendBroadcast(intent);

        if (((int) mTranslationOrder.getServicedTime() / 1000) % HEART_BEAT_INTERVAL == 0) {
            heartBeat();
        }

        L.i(TAG, "更新翻译服务进度：" + new Gson().toJson(mTranslationOrder));
    }

    /**
     * 翻译服务结束
     */
    protected void stopTranslation() {
        // 非自己结束订单不调用修改订单状态接口
        if (mTranslationOrder.getWhoFinish() == TranslationOrder.USER) {
            setTranslationState(mTranslationOrder.getOrderId(), "1");
        }

        Intent intent = new Intent();
        intent.putExtra(TranslationOrder.FINISH_REASON, mTranslationOrder.getFinishReason());
        intent.putExtra(TranslationOrder.WHO_FINISH, mTranslationOrder.getWhoFinish());
        //设置intent的动作为，可以任意定义
        intent.setAction(Action.FINISH);
        //发送无序广播
        sendBroadcast(intent);

        L.i(TAG, "结束翻译服务：" + new Gson().toJson(mTranslationOrder));
    }

    private void setTranslationState(String userOrderId, String phoneState) {
        new TranslationOrderModel().setTranslatorStatus(userOrderId, phoneState)
                .subscribe(result -> {

                }, throwable -> {

                });
    }

    protected void heartBeat() {
        new TranslationOrderModel().heartBeat(mTranslationOrder.getOrderId())
                .subscribe(result -> {
                    if (TextUtils.equals("2", result.getStatus())) {
                        stop(getApplicationContext(), TranslationOrder.OTHER, "订单已结束");
                    }
                }, throwable -> {

                });
    }

    public Timer mTimer;
    public TimerTask mOrderTimerTask = new TimerTask() {
        @Override
        public void run() {
            processTranslation();
        }
    };

    public class Action {
        public static final String START = BuildConfig.APPLICATION_ID + ".translation.order.START";
        public static final String STATUS_CHANGE = BuildConfig.APPLICATION_ID + ".translation.order.STATUS_CHANGE";
        public static final String FINISH = BuildConfig.APPLICATION_ID + ".translation.order.FINISH";

        protected static final String COMMAND_STOP_SERVICE = BuildConfig.APPLICATION_ID + ".translation.order.COMMAND_STOP_SERVICE";
    }

}
