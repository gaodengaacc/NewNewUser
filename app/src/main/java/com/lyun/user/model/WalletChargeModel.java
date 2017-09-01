package com.lyun.user.model;

import com.lyun.api.ErrorParser;
import com.lyun.api.response.APIPageResult;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.WalletChargeBean;
import com.lyun.user.api.request.WalletChargeRecorderBean;
import com.lyun.user.api.response.WalletChargeAliPayResponse;
import com.lyun.user.api.response.WalletChargeRecorderResponse;
import com.lyun.user.api.response.WalletChargeWxPayResponse;
import com.lyun.user.viewmodel.WalletChargeViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Gordon
 * @since 2017/2/23
 * do()
 */

public class WalletChargeModel extends Model {
    //充值接口

    public Observable<WalletChargeAliPayResponse> getAliWalletChargeOrder(String cardId) {
        WalletChargeBean bean = new WalletChargeBean();
        bean.setPayType(WalletChargeViewModel.PayType.ALI.value);
        bean.setCardId(cardId);
        return parseAPIObservable(API.auth.getAliChargeOrder(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
    public Observable<WalletChargeWxPayResponse> getWxWalletChargeOrder(String cardId) {
        WalletChargeBean bean = new WalletChargeBean();
        bean.setPayType(WalletChargeViewModel.PayType.WX.value);
        bean.setCardId(cardId);
        return parseAPIObservable(API.auth.getWxChargeOrder(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    //充值记录
    public Observable<APIResult<APIPageResult<List<WalletChargeRecorderResponse>>>> getWalletChargeRecorder(String cardNo, int pageid) {
        WalletChargeRecorderBean bean = new WalletChargeRecorderBean();
        bean.setCardNo(cardNo);
        bean.setPageid(String.valueOf(pageid));
        bean.setPagesize("20");
        return API.auth.getChargeRecorder(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
