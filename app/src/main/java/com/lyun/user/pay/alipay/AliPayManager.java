package com.lyun.user.pay.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

/**
 * @author Gordon
 * @since 2016/3/1
 * do(支付宝管理类)
 */

public class AliPayManager {
	private OnPayCallBack mPayCallBack;
	public AliPayManager(OnPayCallBack payCallBack) {
		mPayCallBack = payCallBack;
	}
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					if (mPayCallBack != null)
						mPayCallBack.onSuccess();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						if (mPayCallBack != null)
							mPayCallBack.onFailure("支付确认中");
					} else if (TextUtils.equals(resultStatus, "6001")) {
						if (mPayCallBack != null)
							mPayCallBack.onFailure("取消支付");
					} else {
						if (mPayCallBack != null)
							mPayCallBack.onFailure("支付失败");
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				break;
			}
			default:
				break;
			}
		};
	};

	/**
	 * 
	 * @param payInfo
	 */
	public void alipay(Activity activity,final String payInfo) {
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(activity);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo,true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
}
