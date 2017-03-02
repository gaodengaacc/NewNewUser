package com.lyun.user.im.session.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lyun.user.service.TranslationOrder;
import com.lyun.user.service.TranslationOrderService;
import com.lyun.utils.TimeUtil;
import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nim.uikit.session.activity.P2PMessageActivity;
import com.netease.nim.uikit.session.constant.Extras;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * Created by ZHAOWEIWEI on 2017/2/28.
 */

public class TranslationMessageActivity extends P2PMessageActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter intentFilter = new IntentFilter(TranslationOrderService.Action.STATUS_CHANGE);
        registerReceiver(mTranslationOrderStatusChangeReciver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mTranslationOrderStatusChangeReciver);
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
        super.initToolBar();
        centerToolbarTitle(getToolBar());
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


    private BroadcastReceiver mTranslationOrderStatusChangeReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long millis = intent.getLongExtra(TranslationOrder.SERVICED_TIME, 0);
            runOnUiThread(() -> getToolBar().setSubtitle(TimeUtil.convertMills2Str(millis)));
        }
    };

}
