package com.lyun.user.activity;

import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityWalletMainBinding;
import com.lyun.user.viewmodel.WalletMainViewModel;

/**
 * @author Gordon
 * @since 2017/1/9
 * do()
 */

public class WalletMainActivity extends GeneralToolbarActivity<ActivityWalletMainBinding, WalletMainViewModel>{

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_wallet_main;
    }

    @NonNull
    @Override
    protected WalletMainViewModel createBodyViewModel() {

        return new WalletMainViewModel(getTitleViewDataBinding().getMvvm()).setPropertyChangeListener(this);
    }
}
