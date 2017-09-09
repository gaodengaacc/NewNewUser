package com.lyun.user.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.lyun.activity.BaseActivity;
import com.lyun.user.BuildConfig;
import com.lyun.user.eventbusmessage.login.EventWxLoginSuccessMessage;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, BuildConfig.WX_PAY_APPID);
        api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(getApplicationContext(), "认证失败", Toast.LENGTH_LONG).show();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(getApplicationContext(), "登录取消", Toast.LENGTH_LONG).show();
                break;
            case BaseResp.ErrCode.ERR_OK:
                SendAuth.Resp req = (SendAuth.Resp) resp;
                EventBus.getDefault().post(new EventWxLoginSuccessMessage(req.code));

//                         SubscribeMessage.Resp subResp = (SubscribeMessage.Resp) resp;
//                        if(subResp.action.equals("cancel")){
//                            Toast.makeText(getApplicationContext(),"取消授权",Toast.LENGTH_LONG).show();
//                            return;
//                        }else if(subResp.action.equals("confirm")){
//                            String code = resp.openId;
//                            EventBus.getDefault().post(new EventWxLoginSuccessMessage(code));
//                        }
                break;
        }
        finish();
    }

}
