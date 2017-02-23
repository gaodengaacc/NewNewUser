package com.lyun.user.model;

import com.lyun.api.response.APIPageResult;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.WalletChargeBean;
import com.lyun.user.api.request.WalletChargeRecorderBean;
import com.lyun.user.api.response.WalletChargeRecorderResponse;
import com.lyun.user.api.response.WalletChargeResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Gordon
 * @since 2017/2/23
 * do()
 */

public class WalletChargeModel extends Model {
    public Observable<APIResult<WalletChargeResponse>> getWalletChargeOrder(String payType,String handid,String amount,String butTime) {
        WalletChargeBean bean = new WalletChargeBean();
        bean.setPayType(payType);
        bean.setHandid(handid);
        bean.setAmount(amount);
        bean.setBuyTime(butTime);
        return API.auth.getChargeOrder(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
    public Observable<APIResult<APIPageResult<List<WalletChargeRecorderResponse>>>> getWalletChargeRecorder(String cardNo, int pageid) {
        WalletChargeRecorderBean bean = new WalletChargeRecorderBean();
        bean.setCardNo(cardNo);
        bean.setPageid(String.valueOf(pageid));
        bean.setPagesize("20");
        return API.auth.getChargeRecorder(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
