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
import android.view.View;
import android.widget.TextView;

import com.lyun.user.R;
import com.lyun.user.im.session.fragment.TranslationAudioMessageFragment;
import com.lyun.user.service.TranslationOrder;
import com.lyun.user.service.TranslationOrderService;
import com.lyun.user.viewmodel.watchdog.ITranslationAudioMessageViewModelCallbacks;
import com.lyun.utils.TimeUtil;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nim.uikit.session.ToolbarCustomization;
import com.netease.nim.uikit.session.activity.P2PMessageActivity;
import com.netease.nim.uikit.session.constant.Extras;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;

/**
 * Created by ZHAOWEIWEI on 2017/2/28.
 */

public class TranslationMessageActivity extends P2PMessageActivity implements ITranslationAudioMessageViewModelCallbacks {

    private TranslationAudioMessageFragment mTranslationAudioMessageFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(com.netease.nim.uikit.R.id.message_fragment_container, getMessageFragment());
        ft.add(com.netease.nim.uikit.R.id.message_fragment_container, getTranslationAudioMessageFragment());
        ft.commit();

        currentNormalMode = true;

        IntentFilter intentFilter = new IntentFilter(TranslationOrderService.Action.STATUS_CHANGE);
        registerReceiver(mTranslationOrderStatusChangeReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mTranslationOrderStatusChangeReceiver);
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

    @Override
    public void switchMessageMode(BaseObservable observableField, int fieldId) {
        changeToNormalChatMode();
    }
}
