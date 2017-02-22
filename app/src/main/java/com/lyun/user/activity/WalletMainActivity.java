package com.lyun.user.activity;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityWalletMainBinding;
import com.lyun.user.viewmodel.WalletMainViewModel;
import com.lyun.user.viewmodel.watchdog.IWalletMainViewModelCallbacks;

/**
 * @author Gordon
 * @since 2017/1/9
 * do()
 */

public class WalletMainActivity extends GeneralToolbarActivity<ActivityWalletMainBinding, WalletMainViewModel> implements IWalletMainViewModelCallbacks{

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_wallet_main;
    }

    @NonNull
    @Override
    protected WalletMainViewModel createBodyViewModel() {

        return new WalletMainViewModel(getTitleViewDataBinding().getMvvm()).setPropertyChangeListener(this);
    }


    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha;
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void activityBg(ObservableBoolean observableField, int fieldId) {
        if (observableField.get() == true)
            backgroundAlpha(0.5f);
        else
            backgroundAlpha(1f);
    }
}
