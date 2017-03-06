package com.lyun.user.im.session.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lyun.library.mvvm.viewmodel.SimpleDialogViewModel;
import com.lyun.user.R;
import com.lyun.user.im.avchat.AVChatProfile;
import com.lyun.user.im.session.fragment.TranslationAudioMessageFragment;
import com.lyun.user.service.TranslationOrder;
import com.lyun.user.service.TranslationOrderService;
import com.lyun.user.viewmodel.watchdog.ITranslationAudioMessageViewModelCallbacks;
import com.lyun.utils.L;
import com.lyun.utils.TimeUtil;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nim.uikit.session.ToolbarCustomization;
import com.netease.nim.uikit.session.activity.P2PMessageActivity;
import com.netease.nim.uikit.session.constant.Extras;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatEventType;
import com.netease.nimlib.sdk.avchat.constant.AVChatTimeOutEvent;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatCalleeAckEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatCommonEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatNotifyOption;
import com.netease.nimlib.sdk.avchat.model.AVChatOptionalConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;

/**
 * Created by ZHAOWEIWEI on 2017/2/28.
 */

public class TranslationMessageActivity extends P2PMessageActivity implements ITranslationAudioMessageViewModelCallbacks {

    private TranslationAudioMessageFragment mTranslationAudioMessageFragment;
    private String userOrderId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(com.netease.nim.uikit.R.id.message_fragment_container, getMessageFragment());
        ft.add(com.netease.nim.uikit.R.id.message_fragment_container, getTranslationAudioMessageFragment());
        ft.commit();

        currentNormalMode = true;

        registerAVChatListeners();

        // 注册翻译服务广播接收器
        IntentFilter orderStatusChangeIntentFilter = new IntentFilter(TranslationOrderService.Action.STATUS_CHANGE);
        registerReceiver(mTranslationOrderStatusChangeReceiver, orderStatusChangeIntentFilter);

        IntentFilter orderFinishIntentFilter = new IntentFilter(TranslationOrderService.Action.FINISH);
        registerReceiver(mTranslationOrderFinishReceiver, orderFinishIntentFilter);

        userOrderId = getIntent().getStringExtra("orderId");
    }

    @Override
    public void onBackPressed() {
        showIsOver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterAVChatListeners();
        unregisterReceiver(mTranslationOrderStatusChangeReceiver);
        unregisterReceiver(mTranslationOrderFinishReceiver);
    }

    public static void start(Context context, String contactId, String orderId, SessionCustomization customization, IMMessage anchor) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, contactId);
        intent.putExtra("orderId", orderId);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        if (anchor != null) {
            intent.putExtra(Extras.EXTRA_ANCHOR, anchor);
        }
        intent.setClass(context, TranslationMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        context.startActivity(intent);
    }

    @Override
    protected void initToolBar() {

        // 添加右上角切换为语音界面
        ToolbarCustomization toolbarCustomization = getCustomization().getToolbarCustomization();
        ArrayList<ToolbarCustomization.OptionsButton> optionsButtons = toolbarCustomization.getOptionsButtons();
        ToolbarCustomization.OptionsButton avCallButton = new ToolbarCustomization.OptionsButton() {
            @Override
            public void onClick(Context context, View view, String sessionId) {
                if (currentNormalMode) {
                    changeToAudioChatMode();
                } else {
                    changeToNormalChatMode();
                }
            }
        };
        avCallButton.setIconId(R.drawable.ic_av_call);
        optionsButtons.add(avCallButton);
        toolbarCustomization.setOptionsButtons(optionsButtons);
        getCustomization().setToolbarCustomization(toolbarCustomization);

        super.initToolBar();

        centerToolbarTitle(getToolBar());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getToolBar().setPadding(0, ScreenUtil.getStatusBarHeight(this), 0, 0);
        }
    }

    /**
     * 居中显示Toolbar
     *
     * @param toolbar
     */
    public void centerToolbarTitle(final Toolbar toolbar) {
        toolbar.setSubtitle("00:00");
        final CharSequence originalTitle = toolbar.getTitle();
        final CharSequence originalSubtitle = toolbar.getSubtitle();

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);

            if (view instanceof TextView) {
                final TextView textView = (TextView) view;

                if (textView.getText().equals(originalTitle)) {
                    textView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                        textView.setLeft((toolbar.getWidth() - textView.getWidth()) / 2);
                        textView.setRight(textView.getLeft() + textView.getMeasuredWidth());
                    });

                } else if (textView.getText().equals(originalSubtitle)) {
                    textView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                        textView.setLeft((toolbar.getWidth() - textView.getWidth()) / 2);
                        textView.setRight(textView.getLeft() + textView.getMeasuredWidth());
                    });
                }
            }
        }
    }

    private boolean currentNormalMode;

    private void changeToAudioChatMode() {
        if (AVChatProfile.getInstance().isAVChatting()) {
            // 正在语音
            currentNormalMode = false;
            if (getToolBar() != null) {
                getTranslationAudioMessageFragment().setTranslatorName(getToolBar().getTitle().toString());
            }
            switchContent(getTranslationAudioMessageFragment());
            getToolBar().setVisibility(View.GONE);
        } else {
            // 发起语音
            makeAudioCall();
        }
    }

    private void changeToNormalChatMode() {
        currentNormalMode = true;
        switchContent(getMessageFragment());
        getToolBar().setVisibility(View.VISIBLE);
    }

    private TFragment mCurrentFragment;

    @Override
    protected TFragment switchContent(TFragment fragment, boolean needAddToBackStack) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (mCurrentFragment != null) {
            ft.hide(mCurrentFragment);
        }
        ft.show(fragment);
        ft.commit();
        mCurrentFragment = fragment;
        return fragment;
    }

    protected TranslationAudioMessageFragment getTranslationAudioMessageFragment() {
        if (mTranslationAudioMessageFragment == null) {
            mTranslationAudioMessageFragment = new TranslationAudioMessageFragment();
            mTranslationAudioMessageFragment.setContainerId(com.netease.nim.uikit.R.id.message_fragment_container);
        }
        return mTranslationAudioMessageFragment;
    }

    private BroadcastReceiver mTranslationOrderStatusChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long millis = intent.getLongExtra(TranslationOrder.SERVICED_TIME, 0);
            runOnUiThread(() -> {
                String time = TimeUtil.convertMills2Str(millis);
                getToolBar().setSubtitle(time);
                getTranslationAudioMessageFragment().onServiceTimeChanged(time);
            });
        }
    };

    private BroadcastReceiver mTranslationOrderFinishReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    public void switchMessageMode(BaseObservable observableField, int fieldId) {
        changeToNormalChatMode();
    }

    @Override
    public void muteLocalAudio(ObservableBoolean observableField, int fieldId) {
        AVChatManager.getInstance().muteLocalAudio(observableField.get());
    }

    @Override
    public void hangUpAudioCall(ObservableBoolean observableField, int fieldId) {
        hangUpAudioCall();
    }

    @Override
    public void handFreeMode(ObservableBoolean observableField, int fieldId) {
        AVChatManager.getInstance().setSpeaker(observableField.get());
    }

    private void showIsOver() {
        SimpleDialogViewModel viewModel = new SimpleDialogViewModel(this);
        viewModel.setInfo("是否结束本次服务");
        viewModel.setYesBtnText("是");
        viewModel.setCancelBtnText("否");
        viewModel.setOnItemClickListener(new SimpleDialogViewModel.OnItemClickListener() {
            @Override
            public void OnYesClick(View view) {
                // 终止翻译服务
                stopService(new Intent(TranslationMessageActivity.this, TranslationOrderService.class));
            }

            @Override
            public void OnCancelClick(View view) {

            }
        });
        viewModel.show();
    }

    @Override
    public void finish() {
        super.finish();
        if (AVChatProfile.getInstance().isAVChatting()) {
            hangUpAudioCall();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            showIsOver();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    ///////////////////////////语音通话部分//////////////////////////////

    protected void registerAVChatListeners() {
        // 注册audio来电广播接收器
        registerAVChatIncomingCallObserver(true);
        // 监听被叫方回应（主叫方）
        AVChatManager.getInstance().observeCalleeAckNotification(mAVChatCallAckObserver, true);
        // 监听对方挂断（主叫方、被叫方）
        AVChatManager.getInstance().observeHangUpNotification(mAVChatCallHangupObserver, true);
        // 监听呼叫或接听超时通知
        AVChatManager.getInstance().observeTimeoutNotification(mAVChatCallTimeoutObserver, true);
    }

    protected void unregisterAVChatListeners() {
        registerAVChatIncomingCallObserver(false);
        AVChatManager.getInstance().observeCalleeAckNotification(mAVChatCallAckObserver, false);
        AVChatManager.getInstance().observeHangUpNotification(mAVChatCallHangupObserver, false);
        AVChatManager.getInstance().observeTimeoutNotification(mAVChatCallTimeoutObserver, false);
    }

    /**
     * 语音来电自动接听
     *
     * @param register
     */
    private void registerAVChatIncomingCallObserver(boolean register) {
        AVChatManager.getInstance().observeIncomingCall((Observer<AVChatData>) data -> {
            String extra = data.getExtra();
            L.d("AVChat", "Extra Message->" + extra);
            // 仅处理当前服务的语音请求
            if (data.getAccount() == null || !data.getAccount().equals(sessionId)) {
                L.i("AVChat", "收到非当前服务的语音请求，已忽略");
                return;
            }
            SimpleDialogViewModel viewModel = new SimpleDialogViewModel(this);
            viewModel.setInfo("对方发送语音服务请求，是否接受");
            viewModel.setYesBtnText("是");
            viewModel.setCancelBtnText("否");
            viewModel.setOnItemClickListener(new SimpleDialogViewModel.OnItemClickListener() {
                @Override
                public void OnYesClick(View view) {
                    acceptAudioCall();
                }

                @Override
                public void OnCancelClick(View view) {

                }
            });
            viewModel.show();

        }, register);
    }

    /**
     * 发起语音请求
     */
    protected void makeAudioCall() {
        AVChatManager.getInstance().call(sessionId, AVChatType.AUDIO, new AVChatOptionalConfig(), new AVChatNotifyOption(), new AVChatCallback<AVChatData>() {
            @Override
            public void onSuccess(AVChatData avChatData) {
                L.i("AVChat", "语音请求发起成功，等待对方接听");
            }

            @Override
            public void onFailed(int code) {
                L.e("AVChat", "语音请求发起失败 code:" + code);
            }

            @Override
            public void onException(Throwable exception) {
                L.e("AVChat", "语音请求发起失败", exception);
            }
        });
    }

    /**
     * 接听语音通话
     */
    protected void acceptAudioCall() {
        AVChatManager.getInstance().accept(new AVChatOptionalConfig(), new AVChatCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                L.i("AVChat", "语音链接建立成功");
                AVChatProfile.getInstance().setAVChatting(true);
                AVChatManager.getInstance().muteLocalAudio(false);
                AVChatManager.getInstance().muteRemoteAudio(sessionId, false);
                changeToAudioChatMode();
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

    /**
     * 挂断语音通话
     */
    protected void hangUpAudioCall() {
        AVChatManager.getInstance().hangUp(new AVChatCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                L.i("AVChat", "语音挂断成功");
                onAudioHangUp();
            }

            @Override
            public void onFailed(int code) {
                L.i("AVChat", "语音挂断失败，Code:" + code);
            }

            @Override
            public void onException(Throwable exception) {
                L.i("AVChat", "语音挂断失败", exception);
            }
        });
    }

    protected void onAudioHangUp() {
        AVChatProfile.getInstance().setAVChatting(false);
        changeToNormalChatMode();
    }

    /**
     * 监听被叫方回应（主叫方）
     * 主叫方在发起呼叫成功后需要监听被叫方的回应，
     * 监听接口 observeCalleeAckNotification，
     * 回调返回 AVChatCalleeAckEvent，其中包含被叫方的回应结果：
     * <p>
     * 对方拒绝接听;
     * 对方已经有来电了，此时会返回忙;
     * 对方同意接听，此时 SDK 会自动开启音视频设备，建立通话连接，然后双方就可以进行语音视频通话了。
     */
    Observer<AVChatCalleeAckEvent> mAVChatCallAckObserver = (Observer<AVChatCalleeAckEvent>) ackInfo -> {
        if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_BUSY) {
            // 对方正在忙
            L.e("AVChat", "对方正在忙");
        } else if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_REJECT) {
            // 对方拒绝接听
            L.e("AVChat", "对方拒绝接听");
        } else if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_AGREE) {
            // 对方同意接听
            L.i("AVChat", "对方同意接听");
            if (ackInfo.isDeviceReady()) {
                // 设备初始化成功，开始通话
                L.i("AVChat", "设备初始化成功，开始通话");
                AVChatProfile.getInstance().setAVChatting(true);
                AVChatManager.getInstance().muteRemoteAudio(sessionId, false);
                AVChatManager.getInstance().muteLocalAudio(false);
                // 切换到语音聊天界面
                changeToAudioChatMode();
            } else {
                // 设备初始化失败，无法进行通话
                L.e("AVChat", "设备初始化失败，无法进行通话");
            }
        }
    };

    /**
     * 监听对方挂断（主叫方、被叫方）
     * 当被叫方收到来电时（在通话建立之前）需要监听主叫方挂断通知，当双方通话建立之后，都需要监听对方挂断通知来结束本次通话。
     */
    Observer<AVChatCommonEvent> mAVChatCallHangupObserver = (Observer<AVChatCommonEvent>) hangUpInfo -> {
        // 结束通话
        AVChatProfile.getInstance().setAVChatting(false);
        onAudioHangUp();
    };

    /**
     * 监听呼叫或接听超时通知
     * 主叫方在拨打网络通话时，超过 45 秒被叫方还未接听来电，则自动挂断。被叫方超过 45 秒未接听来听，也会自动挂断，在通话过程中网络超时 30 秒自动挂断。
     */
    Observer<AVChatTimeOutEvent> mAVChatCallTimeoutObserver = (Observer<AVChatTimeOutEvent>) event -> {
        // 超时类型
        AVChatProfile.getInstance().setAVChatting(false);
    };
}
