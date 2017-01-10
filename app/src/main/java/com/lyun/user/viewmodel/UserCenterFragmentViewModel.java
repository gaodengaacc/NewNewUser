package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;

/**
 * Created by 郑成裕 on 2016/12/28.
 */

public class UserCenterFragmentViewModel extends ViewModel {
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> userTime = new ObservableField<>();//使用时长
    public final ObservableField<String> userNum = new ObservableField<>();//使用次数
    public final ObservableField<String> userLanguage = new ObservableField<>();//接触语种
    public final ObservableInt exitVisible = new ObservableInt();//退出登录按钮的显示
    private Intent intent;
    public UserCenterFragmentViewModel(){
        init();
    }
    private void init() {
        userName.set("昵称");
        userTime.set("100 分钟");
        userNum.set("20 次");
        userLanguage.set("5 种");
        exitVisible.set(View.VISIBLE);
    }
    public void onViewClick(View view) {
        switch (view.getId()){
            case R.id.user_center_settting:
                intent = new Intent("com.lyun.user.intent.action.USER_SETTING");
                getActivity().startActivity(intent);
                break;
            case R.id.user_center_avatar:
                intent = new Intent("com.lyun.user.intent.action.LOGIN");
                getActivity().startActivity(intent);
                break;
            case R.id.user_center_wallet:
                intent = new Intent("com.lyun.user.intent.action.WALLET_MAIN");
                getActivity().startActivity(intent);
                break;
            case R.id.user_center_exit:
                exit();
                break;
            case R.id.user_center_name:
                login();
                break;
            default:
                break;
        }

    }

    private void login() {
        getActivity().startActivity(new Intent("com.lyun.user.intent.action.LOGIN"));
    }

    private void exit() {
        getProgressDialog().show();
        exitVisible.set(View.INVISIBLE);
        userName.set("点击登录");
    }

}
