package com.lyun.user.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.SimpleDialogViewModel;
import com.lyun.user.Account;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityLoginBinding;
import com.lyun.user.im.NimCache;
import com.lyun.user.im.config.preference.UserPreferences;
import com.lyun.user.service.TranslationOrder;
import com.lyun.user.service.TranslationOrderService;
import com.lyun.user.viewmodel.LoginViewModel;
import com.lyun.user.viewmodel.watchdog.ILoginViewModelCallbacks;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.ClientType;

public class LoginActivity extends GeneralToolbarActivity<ActivityLoginBinding, LoginViewModel>
        implements ILoginViewModelCallbacks {

    private static final String KICK_OUT = "KICK_OUT";
    private SimpleDialogViewModel dialog;

    public static void start(Context context, boolean kickOut) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KICK_OUT, kickOut);
        context.startActivity(intent);
//        if(kickOut)
        Account.preference().clear();
        TranslationOrderService.forceStop(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onParseIntent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null)
            dialog.dismiss();
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
            showKikeDialog(client);
        }
    }

    private void showKikeDialog(String client) {
        if (dialog == null)
            dialog = new SimpleDialogViewModel(this);
        dialog.setInfo(String.format(getString(R.string.kickout_content), client));
        dialog.setYesBtnText("确定");
        dialog.setBtnCancelVisibility(View.GONE);
        dialog.setOnItemClickListener(new SimpleDialogViewModel.OnItemClickListener() {
            @Override
            public void OnYesClick(View view) {
            }

            @Override
            public void OnCancelClick(View view) {
            }
        });
        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (getIntent().getBooleanExtra(KICK_OUT, false)) {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
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
        viewModel.backVisibility.set(View.INVISIBLE);
//        viewModel.onBackClick.set(view -> finish());
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
        Toast.makeText(AppApplication.getInstance(), "登录成功", Toast.LENGTH_LONG).show();
        // 初始化消息提醒配置
        initNotificationConfig();
        //SessionHelper.startP2PSession(this, "123456");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginFailed(ObservableField<Throwable> observableField, int fieldId) {
        Toast.makeText(AppApplication.getInstance(), observableField.get().getMessage(), Toast.LENGTH_LONG).show();
        observableField.get().printStackTrace();
    }

    @Override
    public void onLoginResult(ObservableField<String> observableField, int fieldId) {
        Toast.makeText(AppApplication.getInstance(), observableField.get(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void progressDialogShow(ObservableBoolean observableField, int fieldId) {
        if (observableField.get())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }

    private void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = NimCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }
}
