package com.lyun.user.model;

import com.google.gson.Gson;
import com.lyun.api.ErrorParser;
import com.lyun.api.request.BaseRequest;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.AppApplication;
import com.lyun.user.Constants;
import com.lyun.user.api.API;
import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.login.EventLoginSuccessMessage;
import com.lyun.utils.ACache;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ZHAOWEIWEI on 2017/2/22.
 */

public class LanguageModel extends Model {

    private Observable<APIResult<List<FindLanguageResponse>>> findByLanguage() {
        return API.language.findByLanguage(new BaseRequest())
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public void updateLanguages(boolean isFirst) {
        findByLanguage()
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(listAPIResult -> listAPIResult.isSuccess() && listAPIResult.getContent() != null)
                .subscribe(
                        listAPIResult -> {
                            EventBus.getDefault().post(new EventProgressMessage(false));
                            ACache.get(AppApplication.getInstance()).put(Constants.Cache.SUPPORT_LANGUAGES, new Gson().toJson(listAPIResult.getContent()));
                            if (isFirst) EventBus.getDefault().post(new EventLoginSuccessMessage());
                        }, throwable -> {
                            EventBus.getDefault().post(new EventLoginSuccessMessage());
                        }
                );
    }

}
