package com.lyun.library.mvvm.model;

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
                            public void subscribe(ObservableEmitter<T> e) throws Exception {
                                if ("0".equals(result.getStatus())) {
                                    e.onNext(result.getContent());
                                } else {
                                    e.onError(new Exception(result.getDescribe()));
                                }
                                e.onComplete();
                            }
                        });
                    }
                });
    }

}
