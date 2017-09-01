package com.lyun.user.pay.alipay;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;
import com.lyun.user.eventbusmessage.cardpay.EventPayResultMessage;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Gordon
 * @since 2016/3/1
 * do(支付宝管理类)
 */

public class AliPayManager {
	/**
	 * 
	 * @param payInfo
	 */
	public void alipay(Activity activity,final String payInfo) {
		EventPayResultMessage resultMessage = new EventPayResultMessage();
		Observable.just(payInfo)
				.subscribeOn(Schedulers.newThread())
				.map(pay -> new PayTask(activity).pay(pay, true))
				.subscribeOn(AndroidSchedulers.mainThread())
				.map(result -> new PayResult(result))
				.map(result -> result.getResultStatus())
				.subscribeOn(AndroidSchedulers.mainThread())
				.subscribe(result -> {
					switch (result) {
						case "9000":
							resultMessage.setSuccess(true);
							resultMessage.setMessage("支付成功");
							break;
						case "8000":
							resultMessage.setSuccess(false);
							resultMessage.setMessage("支付确认中");
							break;
						case "6001":
							resultMessage.setSuccess(false);
							resultMessage.setMessage("取消支付");
							break;
						default:
							resultMessage.setSuccess(false);
							resultMessage.setMessage("支付失败");
							break;
					}
					EventBus.getDefault().post(resultMessage);
				});
	}
}
