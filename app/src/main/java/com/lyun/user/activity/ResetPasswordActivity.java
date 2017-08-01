package com.lyun.user.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityResetPasswordBinding;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.viewmodel.ResetPasswordViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author Gordon
 * @since 2017/2/15
 * do()
 */

public class ResetPasswordActivity extends GeneralToolbarActivity<ActivityResetPasswordBinding, ResetPasswordViewModel> {


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
        viewModel.title.set("修改密码");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_reset_password;
    }

    @NonNull
    @Override
    protected ResetPasswordViewModel createBodyViewModel() {
        return new ResetPasswordViewModel().setPropertyChangeListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResetPasswordResult(EventToastMessage message) {
        Toast.makeText(AppApplication.getInstance(), message.getMessage(), Toast.LENGTH_LONG).show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void progressDialogShow(EventProgressMessage message) {
        if (message.getMessage())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }

}
