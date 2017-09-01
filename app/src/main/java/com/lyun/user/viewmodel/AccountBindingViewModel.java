package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.api.response.AccountBindResponse;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.model.LoginModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Gordon
 * @since 2017/7/28
 * do()
 */

public class AccountBindingViewModel extends ViewModel {
    public final ObservableField<String> weiBoText = new ObservableField<>();
    public final ObservableField<String> qqText = new ObservableField<>();
    public final ObservableField<String> wxText = new ObservableField<>();

    public AccountBindingViewModel() {
        init();
    }

    private void init() {
        weiBoText.set("未绑定");
        qqText.set("未绑定");
        wxText.set("未绑定");
        queryBind();
    }

    private void queryBind() {
        new LoginModel().relevanceThird()
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> Observable.create((ObservableOnSubscribe<List<AccountBindResponse>>) observable -> {
                    if (result != null && result.size() > 0) {
                        observable.onNext(result);
                        observable.onComplete();
                    } else observable.onError(new Throwable());
                }))
                .flatMap(result -> Observable.fromIterable(result))
                .map(response -> response.getChannel())
                .subscribe(channel -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    switch (channel) {
                        case AccountBindResponse.QQ_CHANNEL:
                            qqText.set("已绑定");
                            break;
                        case AccountBindResponse.WX_CHANNEL:
                            wxText.set("已绑定");
                            break;
                        case AccountBindResponse.WB_CHANNEL:
                            weiBoText.set("已绑定");
                            break;
                        default:
                            break;
                    }
                }, throwable -> EventBus.getDefault().post(new EventProgressMessage(false)));
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.text_wb:
                break;
            case R.id.text_qq:
                break;
            case R.id.text_wx:
                break;
        }
    }
}
