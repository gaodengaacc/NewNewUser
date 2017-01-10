package com.lyun.user.activity;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityWalletMainBinding;
import com.lyun.user.viewmodel.WalletMainViewModel;

/**
 * @author Gordon
 * @since 2017/1/9
 * do()
 */

public class WalletMainActivity extends GeneralToolbarActivity<ActivityWalletMainBinding, WalletMainViewModel> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addObservableViewModel(getBodyViewModel());
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_wallet_main;
    }

    @NonNull
    @Override
    protected WalletMainViewModel createBodyViewModel() {
        return new WalletMainViewModel(this,getTitleViewDataBinding().getMvvm());
    }

    public void addObservableViewModel(WalletMainViewModel viewModel) {
        viewModel.activityBg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (viewModel.activityBg.get() == true)
                    backgroundAlpha(1f);
                else
                    backgroundAlpha(0.5f);
            }
        });
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha;
        getWindow().setAttributes(layoutParams);
    }
}
