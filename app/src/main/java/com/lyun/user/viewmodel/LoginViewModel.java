package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.command.consumer.Consumer0;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.model.LoginModel;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public class LoginViewModel extends ViewModel {

    public final ObservableField<String> username = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");

    public LoginViewModel(GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel) {
        toolbarViewModel.title.set("登录");
        toolbarViewModel.onBackClick.set(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    public RelayCommand onLoginButtonClick = new RelayCommand(new Consumer0() {
        @Override
        public void accept() throws Exception {
            new LoginModel().login(username.get(), password.get());
        }
    });

}
