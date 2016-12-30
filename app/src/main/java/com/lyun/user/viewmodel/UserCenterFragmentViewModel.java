package com.lyun.user.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.activity.LoginActivity;

/**
 * Created by 郑成裕 on 2016/12/28.
 */

public class UserCenterFragmentViewModel extends ViewModel {
    private Intent intent = new Intent();

    public UserCenterFragmentViewModel(Context context) {
        super(context);
    }

    public void userAvatarImageViewClick(View view) {
        intent.setClass(getContext(), LoginActivity.class);
        getContext().startActivity(intent);
    }

    public void collectTranslatorTextViewClick(View view) {
        intent.setClass(getContext(), LoginActivity.class);
        getContext().startActivity(intent);
    }
}
