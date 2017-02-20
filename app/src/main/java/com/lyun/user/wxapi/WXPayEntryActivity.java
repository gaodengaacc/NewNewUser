package com.lyun.user.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.BuildConfig;
import com.lyun.user.R;
import com.lyun.user.databinding.WxPayResultBinding;
import com.lyun.user.viewmodel.WxPayResultViewModel;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends GeneralToolbarActivity<WxPayResultBinding,WxPayResultViewModel> implements IWXAPIEventHandler {

    private IWXAPI api;
    public static int WXPAY_RESULT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WXPAY_RESULT = -1000;
        api = WXAPIFactory.createWXAPI(this, BuildConfig.WX_PAY_APPID);
        api.handleIntent(getIntent(), this);
    }
    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("微信支付结果");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }
    @Override
    protected int getBodyLayoutId() {
        return R.layout.wx_pay_result;
    }

    @NonNull
    @Override
    protected WxPayResultViewModel createBodyViewModel() {
        return new WxPayResultViewModel();
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
                getBodyViewModel().text.set("支付成功！");
                finish();
            } else if (resp.errCode == BaseResp.ErrCode.ERR_COMM) {
                getBodyViewModel().text.set("支付异常！");
            } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                getBodyViewModel().text.set("中途取消支付！");
            } else if (resp.errCode == BaseResp.ErrCode.ERR_SENT_FAILED) {
                getBodyViewModel().text.set("支付提交失败！");
            } else if (resp.errCode == BaseResp.ErrCode.ERR_AUTH_DENIED) {
                getBodyViewModel().text.set("支付认证失败！");
            } else if (resp.errCode == BaseResp.ErrCode.ERR_UNSUPPORT) {
                getBodyViewModel().text.set("支付异常！");
            } else {
                getBodyViewModel().text.set("支付异常！");
            }
        }
    }
}