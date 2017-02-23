package com.lyun.user.model;

import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.RemainingTimeBean;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 郑成裕 on 2017/2/21.
 */

public class RemainingTimeModel extends Model {
    public Observable<APIResult> getRemainingTime(String cardNo) {
        RemainingTimeBean bean = new RemainingTimeBean(cardNo);
        return API.translationOrder.getRemainingTime(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }


}
