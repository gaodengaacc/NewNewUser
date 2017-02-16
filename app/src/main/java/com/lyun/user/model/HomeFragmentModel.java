package com.lyun.user.model;

import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.FindByLanguageBean;
import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.utils.filecache.Cache;
import com.lyun.utils.filecache.CacheUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Gordon
 * @since 2017/2/16
 * do()
 */

public class HomeFragmentModel extends Model {
    private Observable<APIResult<List<FindLanguageResponse>>> findByLanguage(String pageid, String pagesize) {
        FindByLanguageBean bean = new FindByLanguageBean();
        bean.setPageid(pageid);
        bean.setPagesize(pagesize);
        return API.auth.findByLanguage(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
    public void setFindByLanguage(){
         findByLanguage("0", "20")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        listAPIResult -> {
                            if(listAPIResult.getStatus().equals("0") && listAPIResult.getContent()!=null){
                                List<String> list = new ArrayList<String>();
                                for(FindLanguageResponse response:listAPIResult.getContent()){
                                    list.add(response.getLanguage());
                                }
                                CacheUtil.getInstance().saveData(Cache.DATA_TYPE_FIND_BY_LANGUAGE,list);
                            }


                        }, throwable -> {}
                );
    }
}
