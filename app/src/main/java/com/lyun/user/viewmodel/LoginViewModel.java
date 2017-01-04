package com.lyun.user.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.Button;

import com.lyun.library.mvvm.observable.ObservableActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.activity.ServiceCategoryActivity;
import com.lyun.user.model.LoginModel;

/**
 * Created by ZHAOWEIWEI on 2016/12/21.
 */

public class LoginViewModel extends ViewModel {
    private final int REQUEST_CODE = 10000;
    private Button textView;

    public LoginViewModel(final Context context, GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel) {
        super(context);
        toolbarViewModel.title.set("登录");
        toolbarViewModel.dividerVisibility.set(View.VISIBLE);
        toolbarViewModel.onBackClick.set(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().startActivity(new Intent("com.lyun.user.intent.action.LOGIN")); new Intent("com.lyun.user.intent.action.SERVICE_CATEGORY")
//                Intent intent = new Intent(getContext(), ServiceCategoryActivity.class);
//                intent.putExtra("languageCategory","出行");
//                ObservableActivity.Request request = new ObservableActivity.Request(REQUEST_CODE,intent);
//                getActivity().startActivityForResult(request);
                getActivity().finish();
//                finishActivity();
            }
        });
    }

    public final ObservableField<String> button = new ObservableField<>();

    public final View.OnClickListener onButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            button.set(new LoginModel().getButtonText());
            getToast().show(button.get());
            Intent intent = new Intent(getContext(), ServiceCategoryActivity.class);
            intent.putExtra("languageCategory","出行");
            ObservableActivity.Request request = new ObservableActivity.Request(REQUEST_CODE,intent);
            getActivity().startActivityForResult(request);
        }
    };

    public void setTextView(Button textView) {
        this.textView = textView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
          getToast().show("返回子类成功！！");
        }
    }

}
