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
    public UserCenterFragmentViewModel(Context context) {
        super(context);
    }

    public void userAvatarImageViewClick(View view) {
        Intent intent = new Intent();
        intent.setClass(getContext(), LoginActivity.class);
        getContext().startActivity(intent);
    }
}
