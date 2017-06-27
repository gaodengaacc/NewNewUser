package com.lyun.user.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.BuildConfig;

import net.funol.databinding.watchdog.annotations.WatchThis;


/**
 * Created by 郑成裕 on 2017/3/13.
 */

public class AgreementViewModel extends ViewModel {
    public final ObservableField<String> mWebView = new ObservableField<>("");
    public final ObservableField<Boolean> JsEnabled = new ObservableField<>();
    public final ObservableField<String> titleName = new ObservableField<>("");
    public final ObservableField<Boolean> isClient = new ObservableField<>();
    public final ObservableInt topVisible = new ObservableInt();//android 5.0以上显示，否则不显示
    @WatchThis
    public final BaseObservable backResult = new BaseObservable();
    public AgreementViewModel(Bundle bundle) {
        JsEnabled.set(true);
        isClient.set(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topVisible.set(View.VISIBLE);
        } else {
            topVisible.set(View.GONE);
        }
        if ("charge".equals(bundle.getString("agreementType"))) {
            titleName.set("购买协议");
            mWebView.set(BuildConfig.HOST + "/lytapp/apiDesc/purchasingContract");
        } else {
            titleName.set("注册协议和隐私政策");
            mWebView.set(BuildConfig.HOST + "/lytapp/apiDesc/registrationAndPrivacy");
        }

    }

    public RelayCommand onBack = new RelayCommand(() -> {
        backResult.notifyChange();
    });
}
