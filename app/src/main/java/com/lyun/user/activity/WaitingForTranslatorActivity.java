package com.lyun.user.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityWaittingForTranslatorBinding;
import com.lyun.user.im.session.SessionHelper;
import com.lyun.user.service.TranslationOrder;
import com.lyun.user.service.TranslationOrderService;
import com.lyun.user.viewmodel.WaitingForTranslatorViewModel;
import com.lyun.user.viewmodel.watchdog.IWaitingForTranslatorViewModelCallbacks;

public class WaitingForTranslatorActivity extends MvvmActivity<ActivityWaittingForTranslatorBinding, WaitingForTranslatorViewModel> implements IWaitingForTranslatorViewModelCallbacks {

    private String userOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        userOrderId = getIntent().getStringExtra("userOrderId");

        super.onCreate(savedInstanceState);

        ImageView imageView = (ImageView) findViewById(R.id.waiting_for_translator_time_anim);

        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.av_chat_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if (operatingAnim != null) {
            imageView.startAnimation(operatingAnim);
        }

        IntentFilter intentFilter = new IntentFilter(TranslationOrderService.Action.START);
        registerReceiver(orderStartReceiver, intentFilter);
    }

    private BroadcastReceiver orderStartReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String orderId = intent.getStringExtra(TranslationOrder.ORDER_ID);
            if (userOrderId.equals(orderId)) {
                getActivityViewModel().stopTimer();
                SessionHelper.startTranslationSession(WaitingForTranslatorActivity.this, intent.getStringExtra(TranslationOrder.TRANSLATOR_ID), userOrderId);
                finish();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(orderStartReceiver);
        getActivityViewModel().stopTimer();
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

}
