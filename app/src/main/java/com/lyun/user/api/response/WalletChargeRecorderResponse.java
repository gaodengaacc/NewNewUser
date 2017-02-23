package com.lyun.user.api.response;

/**
 * @author Gordon
 * @since 2017/2/23
 * do()
 */

public class WalletChargeRecorderResponse {
    private int id;
    private String amountNow;
    private String amountNowTime;
    private String payTypeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmountNow() {
        return amountNow;
    }

    public void setAmountNow(String amountNow) {
        this.amountNow = amountNow;
    }

    public String getAmountNowTime() {
        return amountNowTime;
    }

    public void setAmountNowTime(String amountNowTime) {
        this.amountNowTime = amountNowTime;
    }

    public String getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(String payTypeId) {
        this.payTypeId = payTypeId;
    }
}
