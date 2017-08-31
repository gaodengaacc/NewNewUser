package com.lyun.user.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityAccountBindingBinding;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.viewmodel.AccountBindingViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Gordon
 * @since 2017/7/28
 * do()
 */

public class AccountBindingActivity  extends GeneralToolbarActivity<ActivityAccountBindingBinding,AccountBindingViewModel>{
    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_account_binding;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("账号绑定");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }
    @NonNull
    @Override
    protected AccountBindingViewModel createBodyViewModel() {
        dialogViewModel.show();
        return new AccountBindingViewModel();
    }

    @Subscribe
    public void showProgress(EventProgressMessage message) {
        if (message.getMessage())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }
}
