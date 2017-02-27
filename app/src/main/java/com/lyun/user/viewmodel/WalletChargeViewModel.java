package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.R;
import com.lyun.user.api.response.WalletChargeAliPayResponse;
import com.lyun.user.api.response.WalletChargeWxPayResponse;
import com.lyun.user.model.WalletChargeModel;
import com.lyun.user.pay.alipay.OnPayCallBack;
import com.lyun.user.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Gordon
 * @since 2017/1/10
 * do()
 */

public class WalletChargeViewModel extends ViewModel {
    public final ObservableField<String> availableMin = new ObservableField<>();
    public final ObservableField<String> moneyResultText = new ObservableField<>();
    public final ObservableField<String> moneyReduceText = new ObservableField<>();
    public final ObservableField<String> moneyAddText = new ObservableField<>();
    public final ObservableField<String> buyDes = new ObservableField<>();
    public final ObservableInt aliSelect = new ObservableInt();
    public final ObservableInt wxSelect = new ObservableInt();
    public WalletChargeViewModel() {
        init();
    }


    private int PAY_WAY = 2; // 0=绿豆，1=微信，2=支付宝，3=银联，4=总账，5=其他
    private String userOrderId;
    @WatchThis
    public final ObservableField<AliPayInfo> aliPay = new ObservableField();
    @WatchThis
    public final ObservableField<WalletChargeWxPayResponse> wxPay = new ObservableField();
    @WatchThis
    public final ObservableBoolean isShowDialog = new ObservableBoolean();
    @WatchThis
    public final ObservableField<String> showText = new ObservableField();//金额为0时
    @WatchThis
    public final BaseObservable doFinish = new BaseObservable();
    private WalletChargeModel model;
    private void init() {
        availableMin.set("15");
        moneyReduceText.set("15元");
        moneyAddText.set("15元");
        moneyResultText.set("45");
        buyDes.set("1,首次充值-以15分钟为最小购买单元,购买价格为：45元/15分钟;\n2,续费充值-以5分钟为充值单元,购买价格为：15元/5分钟.");
        aliSelect.set(R.mipmap.wallet_charge_select);
        wxSelect.set(R.mipmap.wallet_charge_unselect);
        model = new WalletChargeModel();
        aliPay.set(null);
        wxPay.set(null);
    }


    public void OnClickView(View view) {
        switch (view.getId()) {
            case R.id.wallet_charge_left:
                doReduceOrAdd(false);
                break;
            case R.id.wallet_charge_right:
                doReduceOrAdd(true);
                break;
            case R.id.wallet_charge_ali:
                PAY_WAY = 2;
                aliSelect.set(R.mipmap.wallet_charge_select);
                wxSelect.set(R.mipmap.wallet_charge_unselect);
                break;
            case R.id.wallet_charge_wx:
                PAY_WAY = 1;
                aliSelect.set(R.mipmap.wallet_charge_unselect);
                wxSelect.set(R.mipmap.wallet_charge_select);
                break;
            case R.id.wallet_charge_yes:
                doSubmit();
                break;
            default:
                break;
        }
    }

    //1,首次充值-以15分钟为最小购买单元,购买价格为：45元/分钟;\n2,续费充值-以5分钟为充值单元,购买价格为：15元/分钟.
    private void doSubmit() {
        if (isShowDialog.get()) {
            isShowDialog.set(false);
        }
        isShowDialog.set(true);
            getPayOrder(PAY_WAY);
    }

    private void doReduceOrAdd(boolean isAdd) {
        if (isAdd) {
            moneyResultText.set((Integer.parseInt(moneyResultText.get()) + 15) + "");
            availableMin.set((Integer.parseInt(availableMin.get()) + 5) + "");
        } else {
            if (Integer.parseInt(moneyResultText.get()) <= 45) {
                showText.set("首次充值不能小于45元");
                return;
            }
            moneyResultText.set((Integer.parseInt(moneyResultText.get()) - 15) + "");
            availableMin.set((Integer.parseInt(availableMin.get()) - 5) + "");
        }
    }

    private void getPayOrder(int payType) {
        if (model == null)
            model = new WalletChargeModel();
        model.getWalletChargeOrder(String.valueOf(payType), Account.preference().getPhone(), "0.01", availableMin.get())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        apiResult -> {
                            aliPay.set(null);
                            wxPay.set(null);
                            isShowDialog.set(false);
                            APIResult result = (APIResult) apiResult;
                            if (result.getStatus().equals("0")) {
                                if (payType == 2) {//支付宝
                                    WalletChargeAliPayResponse response = (WalletChargeAliPayResponse) result.getContent();
                                    aliPay.set(new AliPayInfo(callBack, response.getSign()));
                                    userOrderId = response.getUserOrderid();
                                } else if (payType == 1) {//微信
                                    WalletChargeWxPayResponse response = (WalletChargeWxPayResponse) result.getContent();
                                    wxPay.set(response);
                                    userOrderId = response.getUserOrderid();
                                    Account.preference().saveWxAppId(response.getAppid());
                                }
                            } else
                                showText.set(result.getDescribe());

                        }, throwable -> {
                        }
                );
    }

    private OnPayCallBack callBack = new OnPayCallBack() {
        @Override
        public void onSuccess() {
            showText.set("支付成功");
            doPayResult();
        }

        @Override
        public void onFailure(String des) {
            showText.set(des);
        }
    };

    public class AliPayInfo {
        private String sign;
        private OnPayCallBack callBack;

        AliPayInfo(OnPayCallBack callBack, String sign) {
            this.callBack = callBack;
            this.sign = sign;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public OnPayCallBack getCallBack() {
            return callBack;
        }

        public void setCallBack(OnPayCallBack callBack) {
            this.callBack = callBack;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (WXPayEntryActivity.WXPAY_RESULT == BaseResp.ErrCode.ERR_OK && userOrderId != null) //微信充值判断
            doPayResult();
    }

    private void doPayResult() {
        if (model == null)
            model = new WalletChargeModel();
        model.setChargeOrderUpdate(userOrderId, "0")
                .subscribeOn(Schedulers.newThread())
                .subscribe();
        WXPayEntryActivity.WXPAY_RESULT = -1000;
        doFinish.notifyChange();
    }
}
