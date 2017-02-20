package com.lyun.user.pay.wxpay;

import android.app.Activity;

import com.lyun.user.BuildConfig;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author Gordon
 * @since 2016/3/1
 * do(微信支付)
 */
public class WXPayManager {

	private IWXAPI msgApi;
	private Activity mActivity;
	/**
	 * 
	 */
	public WXPayManager(Activity activity) {
		mActivity = activity;
		msgApi = WXAPIFactory.createWXAPI(mActivity, null);
		msgApi.registerApp(BuildConfig.WX_PAY_APPID);
	}

	/**
	 * 发送支付请求
	 * 
	 *
	 */
	public void sendPayReq(String partnerId, String prepayid, String nonce, String timeStamp, String sign) {
		PayReq req = new PayReq();

		req.appId = BuildConfig.WX_PAY_APPID;
		req.partnerId = partnerId;
		req.prepayId = prepayid;
		req.packageValue = "Sign=WXPay";
		req.nonceStr = nonce;
		req.timeStamp = timeStamp;
		req.sign = sign;
		msgApi.registerApp(BuildConfig.WX_PAY_APPID);
		msgApi.sendReq(req);
	}

}
