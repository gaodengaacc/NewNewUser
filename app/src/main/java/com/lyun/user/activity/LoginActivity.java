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

import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.library.mvvm.viewmodel.ProgressBarDialogViewModel;
import com.lyun.library.mvvm.viewmodel.SimpleDialogViewModel;
import com.lyun.user.Account;
import com.lyun.user.AppApplication;
import com.lyun.user.BuildConfig;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityLoginBinding;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.login.EventCheckIsBindMessage;
import com.lyun.user.eventbusmessage.login.EventLoginSuccessMessage;
import com.lyun.user.eventbusmessage.login.EventQqLoginMessage;
import com.lyun.user.eventbusmessage.login.EventRegisterSuccessMessage;
import com.lyun.user.eventbusmessage.login.EventThirdBindPhoneSuccessMessage;
import com.lyun.user.eventbusmessage.login.EventWbLoginMessage;
import com.lyun.user.eventbusmessage.login.EventWxLoginFailedMessage;
import com.lyun.user.eventbusmessage.login.EventWxLoginMessage;
import com.lyun.user.eventbusmessage.login.EventWxLoginSuccessMessage;
import com.lyun.user.im.NimCache;
import com.lyun.user.im.config.preference.UserPreferences;
import com.lyun.user.im.login.NimLoginHelper;
import com.lyun.user.service.TranslationOrderService;
import com.lyun.user.viewmodel.LoginViewModel;
import com.lyun.user.viewmodel.watchdog.ILoginViewModelCallbacks;
import com.lyun.widget.dialog.ProgressBarDialog;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.ClientType;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends MvvmActivity<ActivityLoginBinding, LoginViewModel>
        implements ILoginViewModelCallbacks {

    private static final String KICK_OUT = "KICK_OUT";
    private SimpleDialogViewModel dialog;
    private IWXAPI msgApi;
    private Tencent mTencent;
    private SsoHandler mSsoHandler;
    public static final String THIRD_QQ = "0";
    public static final String THIRD_WX = "1";
    public static final String THIRD_WB = "2";
    private ProgressBarDialog progressBarDialog;

    public static void start(Context context, boolean kickOut) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KICK_OUT, kickOut);
        context.startActivity(intent);
//        if(kickOut)
        Account.preference().clear();
        NimUserInfoCache.getInstance().clear();
        NimLoginHelper.logout();
        TranslationOrderService.forceStop(context);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN)
            Tencent.onActivityResultData(requestCode, resultCode, data, qqListener);
        super.onActivityResult(requestCode, resultCode, data);
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        onParseIntent();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (progressBarDialog != null && progressBarDialog.isShowing())
            progressBarDialog.dismiss();
        getActivityViewModel().clickFlag = false;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void init() {
        msgApi = ((AppApplication) AppApplication.getInstance()).getMsgApi();
        mTencent = ((AppApplication) AppApplication.getInstance()).getTencentApi();
        WbSdk.install(this, new AuthInfo(this, BuildConfig.WB_APPKEY, com.lyun.user.Constants.WB_REDIRECT_URL, com.lyun.user.Constants.WB_SCOPE));
        mSsoHandler = new SsoHandler(this);
        progressBarDialog = new ProgressBarDialog(this, new ProgressBarDialogViewModel(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (dialog != null)
            dialog.dismiss();
        msgApi = null;
        mTencent = null;
        mSsoHandler = null;
        qqListener = null;

    }

    @NonNull
    @Override
    protected LoginViewModel createViewModel() {
        return new LoginViewModel().setPropertyChangeListener(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_login;
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
    public void onNavigationRegister(BaseObservable observableField, int fieldId) {
        startActivity(new Intent(this, RegisterVerifyPhoneActivity.class));
    }

    @Override
    public void onNavigationFindPassword(BaseObservable observableField, int fieldId) {
        startActivity(new Intent(this, FindPasswordActivity.class));
    }


    @Override
    public void onLoginFailed(ObservableField<Throwable> observableField, int fieldId) {
        if (observableField.get().getMessage() != null && !observableField.get().getMessage().equals(""))
        Toast.makeText(AppApplication.getInstance(), observableField.get().getMessage(), Toast.LENGTH_LONG).show();
        observableField.get().printStackTrace();
    }

    @Override
    public void onLoginResult(ObservableField<String> observableField, int fieldId) {
        if (observableField.get() != null && !observableField.get().equals(""))
        Toast.makeText(AppApplication.getInstance(), observableField.get(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void progressDialogShow(ObservableBoolean observableField, int fieldId) {
        if (observableField.get()) {
            if (!progressBarDialog.isShowing())
                progressBarDialog.show();
        }
        else
            progressBarDialog.dismiss();
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

    @Subscribe
    public void onLoginSuccess(EventLoginSuccessMessage message) {
//        Toast.makeText(AppApplication.getInstance(), "登录成功", Toast.LENGTH_LONG).show();
        // 初始化消息提醒配置
        initNotificationConfig();
        //SessionHelper.startP2PSession(this, "123456");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxLoginFailed(EventWxLoginFailedMessage message) {
        Toast.makeText(this, message.getMessage(), Toast.LENGTH_LONG).show();
        getActivityViewModel().clickFlag = false;
        progressBarDialog.dismiss();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void thirdBindSuccess(EventThirdBindPhoneSuccessMessage message) {
        getActivityViewModel().login(true, message.getMessage().openId, message.getMessage().loginType);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void registerSuccess(EventRegisterSuccessMessage message) {
        getActivityViewModel().login(false, message.getMessage(), message.getPassward());
    }
    @Subscribe
    public void onUnBindAccount(EventCheckIsBindMessage message) {
        Intent intent = new Intent(this, RegisterVerifyPhoneActivity.class);
        intent.putExtra("isThird", true);
        intent.putExtra("openId", message.getMessage().openId);
        intent.putExtra("loginType", message.getMessage().loginType);
        startActivity(intent);
    }

    @Subscribe
    public void onWxLogin(EventWxLoginMessage message) {
        wxLogin();
    }

    @Subscribe
    public void onQqLogin(EventQqLoginMessage message) {
        qqLogin();
    }


    @Subscribe
    public void onWbLogin(EventWbLoginMessage message) {
        wbLogin();
    }


    @Subscribe
    public void onWxLoginSuccess(EventWxLoginSuccessMessage message) {
        getActivityViewModel().clickFlag = false;
        getActivityViewModel().getWxOpenId(message.getMessage());
    }

    public void wxLogin() {   //298ae2e8950b6c4ca83ddd4d17a3e97e
        if (!msgApi.isWXAppInstalled()) {
            Toast.makeText(this, "请先安装微信", Toast.LENGTH_LONG).show();
            getActivityViewModel().clickFlag = false;
            if (progressBarDialog != null) progressBarDialog.dismiss();
            return;
        }

        final SendAuth.Req req = new SendAuth.Req();
        req.scope = com.lyun.user.Constants.WX_SCOPE;
        req.state = "wx_login";
        msgApi.sendReq(req);
    }

    @Subscribe
    public void showProgress(EventProgressMessage message) {
        if (message.getMessage()) {
            if (!progressBarDialog.isShowing())
                progressBarDialog.show();
        }
        else
            progressBarDialog.dismiss();
    }

    private void qqLogin() {
        if (mTencent != null)
            mTencent.login(this, com.lyun.user.Constants.QQ_SCOPE, qqListener);
    }

    private void wbLogin() {
        mSsoHandler.authorize(wbListener);
    }

    IUiListener qqListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            getActivityViewModel().clickFlag = false;
            progressBarDialog.dismiss();
            JSONObject json = ((JSONObject) o);
            try {
                String openId = (String) json.get("openid");//"access_token"
                getActivityViewModel().login(true, openId, THIRD_QQ);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            getActivityViewModel().clickFlag = false;
            progressBarDialog.dismiss();
            Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            getActivityViewModel().clickFlag = false;
            progressBarDialog.dismiss();
            Toast.makeText(getBaseContext(), "登录取消", Toast.LENGTH_LONG).show();
        }
    };
    WbAuthListener wbListener = new WbAuthListener() {

        @Override
        public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
            getActivityViewModel().login(true, oauth2AccessToken.getUid(), THIRD_WB);
            getActivityViewModel().clickFlag = false;
            progressBarDialog.dismiss();
        }

        @Override
        public void cancel() {
            getActivityViewModel().clickFlag = false;
            progressBarDialog.dismiss();
            Toast.makeText(getBaseContext(), "登录取消", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
            getActivityViewModel().clickFlag = false;
            progressBarDialog.dismiss();
            Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_LONG).show();
        }
    };

}
