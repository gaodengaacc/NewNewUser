package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.view.View;

import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.AppIntent;
import com.lyun.user.R;
import com.lyun.user.api.response.WalletChargeWxPayResponse;
import com.lyun.user.model.WalletChargeModel;
import com.lyun.user.pay.alipay.OnPayCallBack;
import com.lyun.user.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import net.funol.databinding.watchdog.annotations.WatchThis;

/**
 * @author Gordon
 * @since 2017/1/10
 * do()
 */

public class WalletChargeViewModel extends ViewModel {
    public final ObservableField<String> availableMin = new ObservableField<>();//购买时长
    public final ObservableField<String> moneyResultText = new ObservableField<>();//购买金额
    public final ObservableField<String> moneyReduceText = new ObservableField<>();//金额增加显示
    public final ObservableField<String> moneyAddText = new ObservableField<>();//金额减少显示
    public final ObservableField<String> buyDes = new ObservableField<>();//购买说明
    public final ObservableInt aliSelect = new ObservableInt();//充值图标
    public final ObservableInt wxSelect = new ObservableInt();
    private long unUseTime;//传来的剩余时间
    private long lastClickTime;

    public WalletChargeViewModel(long unUseTime) {
        this.unUseTime = unUseTime;
        init();
    }


    private PayType PAY_WAY = PayType.ALI; // 0=绿豆，1=微信，2=支付宝，3=银联，4=总账，5=其他
    private String userOrderId;//订单id
    @WatchThis
    public final ObservableField<String> aliPay = new ObservableField(); //支付宝
    @WatchThis
    public final ObservableField<WalletChargeWxPayResponse> wxPay = new ObservableField();//微信
    @WatchThis
    public final ObservableBoolean isShowDialog = new ObservableBoolean();//加载的dialog
    @WatchThis
    public final ObservableField<String> showText = new ObservableField();//展示显示的文字
    @WatchThis
    public final BaseObservable doFinish = new BaseObservable();//结束Activity
    private WalletChargeModel model;

    //初始化界面显示
    private void init() {

        moneyReduceText.set("15元");
        moneyAddText.set("15元");
        if (unUseTime > 0) {
            availableMin.set("5");
            moneyResultText.set("15");
        } else {
            availableMin.set("15");
            moneyResultText.set("45");
        }
        buyDes.set("1,首次充值-以15分钟为最小购买单元,购买价格为：45元/15分钟;\n2,续费充值-以5分钟为充值单元,购买价格为：15元/5分钟.");
        aliSelect.set(R.mipmap.wallet_charge_select);
        wxSelect.set(R.mipmap.wallet_charge_unselect);
        model = new WalletChargeModel();
    }

    //activity 干掉以后恢复数据用
    public void reSetData(String availableMin, String moneyResultText) {
        this.availableMin.set(availableMin);
        this.moneyResultText.set(moneyResultText);
    }

    //点击事件
    public void OnClickView(View view) {
        switch (view.getId()) {
            case R.id.wallet_charge_left:
                doReduceOrAdd(false);
                break;
            case R.id.wallet_charge_right:
                doReduceOrAdd(true);
                break;
            case R.id.wallet_charge_ali:
                PAY_WAY = PayType.ALI;
                aliSelect.set(R.mipmap.wallet_charge_select);
                wxSelect.set(R.mipmap.wallet_charge_unselect);
                break;
            case R.id.wallet_charge_wx:
                PAY_WAY = PayType.WX;
                aliSelect.set(R.mipmap.wallet_charge_unselect);
                wxSelect.set(R.mipmap.wallet_charge_select);
                break;
            case R.id.wallet_charge_protocol:
                Intent intent = new Intent(AppIntent.ACTION_AGREEMENT);
                Bundle bundle = new Bundle();
                bundle.putString("agreementType", "charge");
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                break;
            case R.id.wallet_charge_yes:
                if (isFastDoubleClick())
                    return;
                doSubmit();
                break;
            default:
                break;
        }
    }

    //重置按钮点击事件
    private void doSubmit() {
        if (isShowDialog.get()) {
            isShowDialog.set(false);
        }
        isShowDialog.set(true);
        getPayOrder(PAY_WAY);
    }

    //增加和减少金额按钮的事件
    private void doReduceOrAdd(boolean isAdd) {
        if (isAdd) {
            moneyResultText.set((Integer.parseInt(moneyResultText.get()) + 15) + "");
            availableMin.set((Integer.parseInt(availableMin.get()) + 5) + "");
        } else {
            if ((Integer.parseInt(moneyResultText.get()) <= 45 && unUseTime <= 0) || (Integer.parseInt(moneyResultText.get()) <= 15)) {
                return;
            }
            moneyResultText.set((Integer.parseInt(moneyResultText.get()) - 15) + "");
            availableMin.set((Integer.parseInt(availableMin.get()) - 5) + "");
        }
    }

    //获取支付订单信息
    private void getPayOrder(PayType payType) {
        if (model == null)
            model = new WalletChargeModel();
//        model.getWalletChargeOrder(payType.value, Account.preference().getPhone(), moneyResultText.get(), availableMin.get())
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        apiResult -> {
//                            isShowDialog.set(false);
//                            APIResult result = (APIResult) apiResult;
//                            if (result.isSuccess()) {
//                                if (payType == PayType.ALI) {//支付宝
//                                    WalletChargeAliPayResponse response = (WalletChargeAliPayResponse) result.getContent();
//                                    ObservableNotifier.alwaysNotify(aliPay, response.getSign());
//                                    userOrderId = response.getUserOrderid();
//                                } else if (payType == PayType.WX) {//微信
//                                    WalletChargeWxPayResponse response = (WalletChargeWxPayResponse) result.getContent();
//                                    ObservableNotifier.alwaysNotify(wxPay, response);
//                                    userOrderId = response.getUserOrderid();
//                                    Account.preference().saveWxAppId(response.getAppid());
//                                }
//                            } else {
//                                ObservableNotifier.alwaysNotify(showText, result.getDescribe());
//                            }
//
//                        }, throwable -> {
//                        }
//                );
    }

    //支付宝回调
    private OnPayCallBack callBack = new OnPayCallBack() {
        @Override
        public void onSuccess() {
            System.out.print("ALi callBack success");
            ObservableNotifier.alwaysNotify(showText, "支付成功");
            doPayResult();
        }

        @Override
        public void onFailure(String des) {
            System.out.print("ALi callBack failure");
            ObservableNotifier.alwaysNotify(showText, des);
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        if (WXPayEntryActivity.WXPAY_RESULT == BaseResp.ErrCode.ERR_OK && userOrderId != null) //微信充值判断
            doPayResult();
    }

    //充值成功通知后台去做
    private void doPayResult() {
        WXPayEntryActivity.WXPAY_RESULT = -1000;
        doFinish.notifyChange();
    }

    //充值类型枚举
    public enum PayType {
        ALI("2"),//支付宝
        WX("1");//微信
        public String value;

        PayType(String value) {
            this.value = value;
        }
    }

    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
