package com.lyun.user.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityRegisterVerifyPhoneBinding;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.eventbusmessage.login.EventThirdBindPhoneSuccessMessage;
import com.lyun.user.eventbusmessage.login.EventThirdPhoneIsRegisterMessage;
import com.lyun.user.viewmodel.RegisterVerifyPhoneViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class RegisterVerifyPhoneActivity extends GeneralToolbarActivity<ActivityRegisterVerifyPhoneBinding, RegisterVerifyPhoneViewModel> {
    private String openId;
    private String loginType;
    private boolean isThird;

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_register_verify_phone;
    }

    @NonNull
    @Override
    protected RegisterVerifyPhoneViewModel createBodyViewModel() {
        isThird = getIntent().getBooleanExtra("isThird", false);
        openId = getIntent().getStringExtra("openId");
        loginType = getIntent().getStringExtra("loginType");
        return new RegisterVerifyPhoneViewModel(isThird, openId, loginType)
                .setPropertyChangeListener(this);
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
        if (getIntent().getBooleanExtra("isThird", false)) viewModel.title.set("绑定手机号码");
        else viewModel.title.set("快速注册");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }
    @Subscribe
    public void isThirdPhoneRegister(EventThirdPhoneIsRegisterMessage message) {
        if (message.getMessage())
            EventBus.getDefault().
                    post(new EventThirdBindPhoneSuccessMessage(new EventThirdBindPhoneSuccessMessage.BindMessage(openId, loginType)));
        else startActivity(getBodyViewModel().intent);
        finish();
    }
    @Subscribe
    public void showDialog(EventProgressMessage message){
        if (message.getMessage())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }
    @Subscribe
    public void showToast(EventToastMessage message){
        Toast.makeText(AppApplication.getInstance(),message.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
