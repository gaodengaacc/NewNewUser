package com.lyun.user.pay.alipay;

/**
 * @author Gordon
 * @since 2016/3/1
 * do()
 */
public interface OnPayCallBack {

    void onSuccess();

    void onFailure(String des);

}
