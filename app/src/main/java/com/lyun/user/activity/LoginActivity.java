package com.lyun.user.activity;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.watchdog.NotifyThis;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityLoginBinding;
import com.lyun.user.viewmodel.LoginViewModel;
import com.lyun.utils.L;

public class LoginActivity extends GeneralToolbarActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_login;
    }

    @NonNull
    @Override
    protected LoginViewModel createBodyViewModel() {
        LoginViewModel model = new LoginViewModel(getTitleViewDataBinding().getMvvm());
        model.setPropertyChangeListener(this);
        return model;
    }

    @NotifyThis
    public void onLoginSuccess(ObservableField<String> observableField,int fieldId) {
        Toast.makeText(this, "登录成功啦！", Toast.LENGTH_LONG).show();
        L.e("tag", "登录成功啦！");
    }

}
