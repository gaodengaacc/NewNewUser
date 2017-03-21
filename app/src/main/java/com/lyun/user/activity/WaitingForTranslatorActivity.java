package com.lyun.user.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityWaittingForTranslatorBinding;
import com.lyun.user.im.avchat.AVChatProfile;
import com.lyun.user.im.session.SessionHelper;
import com.lyun.user.model.TranslationOrderModel;
import com.lyun.user.service.TranslationOrder;
import com.lyun.user.service.TranslationOrderService;
import com.lyun.user.viewmodel.WaitingForTranslatorViewModel;
import com.lyun.user.viewmodel.watchdog.IWaitingForTranslatorViewModelCallbacks;
import com.lyun.utils.L;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatOptionalConfig;

public class WaitingForTranslatorActivity extends MvvmActivity<ActivityWaittingForTranslatorBinding, WaitingForTranslatorViewModel> implements IWaitingForTranslatorViewModelCallbacks {

    private String userOrderId;
    private String targetLanguage;
    private TranslationOrderModel.OrderType orderType;
    private String translatorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        userOrderId = getIntent().getStringExtra(TranslationOrder.ORDER_ID);
        targetLanguage = getIntent().getStringExtra(TranslationOrder.TARGET_LANGUAGE);
        orderType = (TranslationOrderModel.OrderType) getIntent().getSerializableExtra(TranslationOrder.ORDER_TYPE);

        super.onCreate(savedInstanceState);

        ImageView imageView = (ImageView) findViewById(R.id.waiting_for_translator_time_anim);

        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.av_chat_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if (operatingAnim != null) {
            imageView.startAnimation(operatingAnim);
        }

        IntentFilter intentFilter = new IntentFilter(TranslationOrderService.Action.START);
        registerReceiver(mOrderStartReceiver, intentFilter);

        // 注册audio来电广播接收器
        AVChatManager.getInstance().observeIncomingCall(mIncomingCallObserver, true);
    }

    private BroadcastReceiver mOrderStartReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String orderId = intent.getStringExtra(TranslationOrder.ORDER_ID);
            if (userOrderId.equals(orderId)) {
                String account = intent.getStringExtra(TranslationOrder.TRANSLATOR_ID);
                SessionHelper.startTranslationSession(WaitingForTranslatorActivity.this, account, userOrderId, orderType, targetLanguage);
                finish();
            } else {
                TranslationOrderService.stop(context, TranslationOrder.OTHER, "非当前订单，终止该异常订单");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mOrderStartReceiver);
        AVChatManager.getInstance().observeIncomingCall(mIncomingCallObserver, false);
        getActivityViewModel().onHangUpCheckCommand.execute(true);
    }

    @NonNull
    @Override
    protected WaitingForTranslatorViewModel createViewModel() {
        return new WaitingForTranslatorViewModel(userOrderId).setPropertyChangeListener(this);
    }

    @NonNull
    @Override
    protected ViewModel getBodyViewModel() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_waitting_for_translator;
    }

    @Override
    public void onOrderCanceled(ObservableField<String> observableField, int fieldId) {
        runOnUiThread(() -> {
            if (observableField.get() != null && !TextUtils.isEmpty(observableField.get())) {
                Toast.makeText(this, observableField.get(), Toast.LENGTH_LONG).show();
            }
            finish();
        });
    }

    /**
     * 语音来电自动接听
     *
     * @param register
     */
    protected Observer<AVChatData> mIncomingCallObserver = new Observer<AVChatData>() {
        @Override
        public void onEvent(AVChatData data) {
            L.d("AVChat", "接收到语音请求 -> " + new Gson().toJson(data));
            translatorId = data.getAccount();
            acceptAudioCall();
        }
    };

    /**
     * 接听语音通话
     */
    protected void acceptAudioCall() {
        L.i("AVChat", "尝试建立语音连接");
        AVChatManager.getInstance().accept(new AVChatOptionalConfig(), new AVChatCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                L.i("AVChat", "语音链接建立成功");
                AVChatProfile.getInstance().setAVChatting(true);
                AVChatManager.getInstance().muteLocalAudio(getActivityViewModel().muteMode);
                AVChatManager.getInstance().setSpeaker(getActivityViewModel().handFreeMode);

                TranslationOrderService.start(getApplicationContext(),
                        userOrderId,
                        targetLanguage,
                        TranslationOrderModel.OrderType.AUDIO,
                        translatorId,
                        Account.preference().getPhone());
            }

            @Override
            public void onFailed(int code) {
                L.e("AVChat", "语音链接建立失败,Code:" + code);
            }

            @Override
            public void onException(Throwable exception) {
                L.e("AVChat", "语音链接建立失败", exception);
            }
        });
    }

}
