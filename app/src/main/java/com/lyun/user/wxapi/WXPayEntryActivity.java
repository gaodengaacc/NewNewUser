package com.lyun.user.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.lyun.activity.BaseActivity;
import com.lyun.user.AppApplication;
import com.lyun.user.eventbusmessage.cardpay.EventPayResultMessage;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    public static int WXPAY_RESULT = -1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = ((AppApplication) AppApplication.getInstance()).getMsgApi();
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            EventPayResultMessage message = new EventPayResultMessage();
            message.setSuccess(false);
            WXPAY_RESULT = resp.errCode;
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                message.setSuccess(true);
                message.setMessage("支付成功");
            } else if (resp.errCode == BaseResp.ErrCode.ERR_COMM) {
                message.setMessage("支付异常");
            } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                message.setMessage("取消支付");
            } else if (resp.errCode == BaseResp.ErrCode.ERR_SENT_FAILED) {
                message.setMessage("支付提交失败");
            } else if (resp.errCode == BaseResp.ErrCode.ERR_AUTH_DENIED) {
                message.setMessage("支付认证失败");
            } else if (resp.errCode == BaseResp.ErrCode.ERR_UNSUPPORT) {
                message.setMessage("支付异常");
            } else {
                message.setMessage("支付异常");
            }
            EventBus.getDefault().post(message);
            finish();
        }
    }
}