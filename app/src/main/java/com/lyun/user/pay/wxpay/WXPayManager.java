package com.lyun.user.pay.wxpay;

import android.app.Activity;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author Gordon
 * @since 2016/3/1
 * do(微信支付)
 */
public class WXPayManager {
	/**
	 * 
	 */
	public WXPayManager() {
	}

	/**
	 * 发送支付请求
	 * 
	 *
	 */
	public void sendPayReq(Activity activity,String appId,String partnerId, String prepayid, String nonce, String timeStamp, String sign) {
		IWXAPI	msgApi = WXAPIFactory.createWXAPI(activity, null);
		msgApi.registerApp(appId);
		PayReq req = new PayReq();
		req.appId = appId;
		req.partnerId = partnerId;
		req.prepayId = prepayid;
		req.packageValue = "Sign=WXPay";
		req.nonceStr = nonce;
		req.timeStamp = timeStamp;
		req.sign = sign;
		msgApi.sendReq(req);
	}

}
