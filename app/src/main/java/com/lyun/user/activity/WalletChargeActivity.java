package com.lyun.user.activity;

import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityWalletChargeBinding;
import com.lyun.user.viewmodel.WalletChargeViewModel;

/**
 * @author Gordon
 * @since 2017/1/10
 * do()
 */

public class WalletChargeActivity  extends GeneralToolbarActivity<ActivityWalletChargeBinding,WalletChargeViewModel>{
    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_wallet_charge;
    }

    @NonNull
    @Override
    protected WalletChargeViewModel createBodyViewModel() {
        return new WalletChargeViewModel(getTitleViewDataBinding().getMvvm());
    }
}
