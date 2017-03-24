package com.lyun.user.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lyun.activity.BaseActivity;
import com.lyun.user.Account;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    public static int WXPAY_RESULT = -1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, (Account.preference().getWxAppId()==null || Account.preference().getWxAppId().equals(""))?"wx32783f5b7b05f691":Account.preference().getWxAppId());
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
            WXPAY_RESULT = resp.errCode;
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_LONG).show();
            } else if (resp.errCode == BaseResp.ErrCode.ERR_COMM) {
                Toast.makeText(getApplicationContext(), "支付异常", Toast.LENGTH_LONG).show();
            } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                Toast.makeText(getApplicationContext(), "取消支付", Toast.LENGTH_LONG).show();
            } else if (resp.errCode == BaseResp.ErrCode.ERR_SENT_FAILED) {
                Toast.makeText(getApplicationContext(), "支付提交失败", Toast.LENGTH_LONG).show();
            } else if (resp.errCode == BaseResp.ErrCode.ERR_AUTH_DENIED) {
                Toast.makeText(getApplicationContext(), "支付认证失败", Toast.LENGTH_LONG).show();
            } else if (resp.errCode == BaseResp.ErrCode.ERR_UNSUPPORT) {
                Toast.makeText(getApplicationContext(), "支付异常", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "支付异常", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }
}