package com.lyun.library.mvvm.model;

import com.lyun.api.exception.APINotSuccessException;
import com.lyun.api.response.APIResult;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public class Model {

    /**
     * 接口数据处理
     * 将接口返回状态码为失败的接口，抛出异常给onError执行
     *
     * @param observable
     * @param <T>
     * @return
     */
    public <T> Observable<T> parseAPIObservable(Observable<APIResult<T>> observable) {
        return observable
                .flatMap(new Function<APIResult<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(final APIResult<T> result) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<T>() {
                            @Override
                            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                                if (result.isSuccess()) {
                                    emitter.onNext(result.getContent());
                                    emitter.onComplete();
                                } else {
                                    emitter.onError(new APINotSuccessException(result));
                                }
                            }
                        });
                    }
                });
    }

    /**
     * 接口数据处理
     * 将接口返回状态码为失败的接口，抛出异常给onError执行
     *
     * @param observable
     * @param
     * @return
     */
    public Observable parseNullObservable(Observable<APIResult> observable) {
        return observable.flatMap(new Function<APIResult, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(final APIResult apiResult) throws Exception {
                return Observable.create(new ObservableOnSubscribe<Object>() {

                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        if (apiResult.isSuccess()) {
                            e.onNext(apiResult);
                            e.onComplete();
                        } else {
                            e.onError(new APINotSuccessException(apiResult));
                        }
                    }
                });
            }
        });

    }

}
