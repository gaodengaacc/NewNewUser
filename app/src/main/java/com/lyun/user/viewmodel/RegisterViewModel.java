package com.lyun.user.viewmodel;

import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.model.RegisterModel;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ZHAOWEIWEI on 2017/1/16.
 */

public class RegisterViewModel extends ViewModel {
    public final ObservableField<String> username = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");
    public final ObservableField<String> comfirmPassword = new ObservableField<>("");

    public RelayCommand onRegisterButtonClick = new RelayCommand(() -> {
        register(username.get(), password.get());
    });

    private void register(String username, String password) {
        new RegisterModel().register(username, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
