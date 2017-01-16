package com.lyun.user.viewmodel;

import android.databinding.ObservableField;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.model.LoginModel;
import com.lyun.utils.L;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public class LoginViewModel extends ViewModel {

    public final ObservableField<String> username = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");

    public LoginViewModel(GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel) {
        toolbarViewModel.title.set("登录");
        toolbarViewModel.onBackClick.set(view -> getActivity().finish());
    }

    @WatchThis
    public final ObservableField<String> onLoginSuccess = new ObservableField<>();

    public RelayCommand onLoginButtonClick = new RelayCommand(() -> {
        new LoginModel().login(username.get(), password.get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponseAPIResult -> onLoginSuccess.notifyChange(), throwable -> {
                    onLoginSuccess.notifyChange();
                    L.e("tag", throwable);
                });
    });

}
