package com.lyun.user.activity;

import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
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
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("购买");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }
    @NonNull
    @Override
    protected WalletChargeViewModel createBodyViewModel() {
        return new WalletChargeViewModel().setPropertyChangeListener(this);
    }
}
