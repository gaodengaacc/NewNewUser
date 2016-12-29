package com.lyun.user.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.Button;

import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.model.LoginModel;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public class LoginViewModel extends ViewModel {

    private Button textView;

    public LoginViewModel(final Context context, GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel) {
        super(context);
        toolbarViewModel.title.set("登录");
        toolbarViewModel.dividerVisibility.set(View.VISIBLE);
        toolbarViewModel.onBackClick.set(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent("com.lyun.user.intent.action.LOGIN"));
            }
        });
    }

    public final ObservableField<String> button = new ObservableField<>();

    public final View.OnClickListener onButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            button.set(new LoginModel().getButtonText());
            getToast().show(button.get());
        }
    };

    public void setTextView(Button textView) {
        this.textView = textView;
    }
}
