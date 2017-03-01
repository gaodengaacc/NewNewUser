package com.lyun.user.activity;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.lyun.user.viewmodel.WaitingForTranslatorViewModel;
import com.lyun.user.viewmodel.watchdog.IWaitingForTranslatorViewModelCallbacks;

public class WaitingForTranslatorActivity extends MvvmActivity implements IWaitingForTranslatorViewModelCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = (ImageView) findViewById(R.id.waiting_for_translator_time_anim);

        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.av_chat_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if (operatingAnim != null) {
            imageView.startAnimation(operatingAnim);
        }
    }

    @NonNull
    @Override
    protected ViewModel createViewModel() {
        return new WaitingForTranslatorViewModel(getIntent().getStringExtra("userOrderId")).setPropertyChangeListener(this);
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
    public void onOrderCanceledOnTimeOut(ObservableField<String> observableField, int fieldId) {
        if (observableField.get() != null && !TextUtils.isEmpty(observableField.get())) {
            Toast.makeText(this, observableField.get(), Toast.LENGTH_LONG).show();
        }
        finish();
    }

}
