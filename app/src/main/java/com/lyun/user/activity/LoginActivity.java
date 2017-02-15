package com.lyun.user.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityLoginBinding;
import com.lyun.user.im.session.SessionHelper;
import com.lyun.user.viewmodel.LoginViewModel;
import com.lyun.user.viewmodel.watchdog.ILoginViewModelCallbacks;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nim.uikit.session.activity.P2PMessageActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.ClientType;

public class LoginActivity extends GeneralToolbarActivity<ActivityLoginBinding, LoginViewModel>
        implements ILoginViewModelCallbacks {

    private static final String KICK_OUT = "KICK_OUT";

    public static void start(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void start(Context context, boolean kickOut) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(KICK_OUT, kickOut);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        onParseIntent();
    }

    private void onParseIntent() {
        if (getIntent().getBooleanExtra(KICK_OUT, false)) {
            int type = NIMClient.getService(AuthService.class).getKickedClientType();
            String client;
            switch (type) {
                case ClientType.Web:
                    client = "网页端";
                    break;
                case ClientType.Windows:
                    client = "电脑端";
                    break;
                case ClientType.REST:
                    client = "服务端";
                    break;
                default:
                    client = "移动端";
                    break;
            }
            EasyAlertDialogHelper.showOneButtonDiolag(LoginActivity.this, getString(R.string.kickout_notify),
                    String.format(getString(R.string.kickout_content), client), getString(R.string.ok), true, null);
        }
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_login;
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("登录");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @NonNull
    @Override
    protected LoginViewModel createBodyViewModel() {
        return new LoginViewModel().setPropertyChangeListener(this);
    }

    @Override
    public void onNavigationRegister(BaseObservable observableField, int fieldId) {
        startActivity(new Intent(this, RegisterVerifyPhoneActivity.class));
    }

    @Override
    public void onNavigationFindPassword(BaseObservable observableField, int fieldId) {
        startActivity(new Intent(this, FindPasswordActivity.class));
    }

    @Override
    public void onLoginSuccess(BaseObservable observableField, int fieldId) {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
        SessionHelper.startP2PSession(this, "123456");
        finish();
    }

    @Override
    public void onLoginFailed(ObservableField<Throwable> observableField, int fieldId) {
        Toast.makeText(this, observableField.get().getMessage(), Toast.LENGTH_LONG).show();
        observableField.get().printStackTrace();
    }

}
