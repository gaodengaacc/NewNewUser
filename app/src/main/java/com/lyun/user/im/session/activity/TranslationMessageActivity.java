package com.lyun.user.im.session.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.BaseObservable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lyun.library.mvvm.viewmodel.SimpleDialogViewModel;
import com.lyun.user.R;
import com.lyun.user.im.NimCache;
import com.lyun.user.im.avchat.AVChatProfile;
import com.lyun.user.im.avchat.activity.AVChatActivity;
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
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
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

        // 注册audio来电广播接收器
        registerAVChatIncomingCallObserver(true);

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
        registerAVChatIncomingCallObserver(false);
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

        AVChatManager.getInstance().call(sessionId, AVChatType.AUDIO, new AVChatOptionalConfig(), new AVChatNotifyOption(), new AVChatCallback<AVChatData>() {
            @Override
            public void onSuccess(AVChatData avChatData) {
                L.e("AVChat", "Success" + new Gson().toJson(avChatData));
            }

            @Override
            public void onFailed(int code) {
                L.e("AVChat", "failed" + code);
            }

            @Override
            public void onException(Throwable exception) {
                L.e("AVChat", exception);
            }
        });

        currentNormalMode = false;
        if (getToolBar() != null) {
            getTranslationAudioMessageFragment().setTranslatorName(getToolBar().getTitle().toString());
        }
        switchContent(getTranslationAudioMessageFragment());
        getToolBar().setVisibility(View.GONE);
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

    private void registerAVChatIncomingCallObserver(boolean register) {
        AVChatManager.getInstance().observeIncomingCall((Observer<AVChatData>) data -> {
            String extra = data.getExtra();
            L.e("Extra", "Extra Message->" + extra);
            // 有网络来电打开AVChatActivity
            AVChatProfile.getInstance().setAVChatting(true);
            AVChatActivity.launch(NimCache.getContext(), data, AVChatActivity.FROM_BROADCASTRECEIVER);
        }, register);
    }

    @Override
    public void switchMessageMode(BaseObservable observableField, int fieldId) {
        changeToNormalChatMode();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            showIsOver();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
