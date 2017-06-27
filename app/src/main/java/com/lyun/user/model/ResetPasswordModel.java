package com.lyun.user.model;

import com.lyun.api.ErrorParser;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.model.Model;
import com.lyun.user.api.API;
import com.lyun.user.api.request.ResetPasswordBean;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Gordon
 * @since 2017/2/15
 * do()
 */

public class ResetPasswordModel extends Model {
    public Observable<APIResult> resetPassword(String username, String password,String newPassword) {
        ResetPasswordBean bean = new ResetPasswordBean();
        bean.setUserName(username);
        bean.setPassword(password);
        bean.setNewPassword(newPassword);
        return API.auth.resetPassword(bean)
                .onErrorReturn(throwable -> ErrorParser.mockResult(throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
