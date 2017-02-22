package com.lyun.user.model;

import com.lyun.api.request.BaseRequest;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.utils.filecache.Cache;
import com.lyun.utils.filecache.CacheUtil;

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
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public void updateLanguages() {
        findByLanguage()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        listAPIResult -> {
                            if (listAPIResult.getStatus().equals("0") && listAPIResult.getContent() != null) {
                                CacheUtil.getInstance().saveData(Cache.DATA_TYPE_FIND_BY_LANGUAGE, listAPIResult.getContent());
                            }
                        }, throwable -> {
                        }
                );
    }

}
