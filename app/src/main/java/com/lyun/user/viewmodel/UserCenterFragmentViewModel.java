package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Build;
import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.R;
import com.lyun.user.model.StatisticsCardNoModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by 郑成裕 on 2016/12/28.
 */

public class UserCenterFragmentViewModel extends ViewModel {

    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> userTime = new ObservableField<>();//使用时长
    public final ObservableField<String> userNum = new ObservableField<>();//使用次数
    public final ObservableField<String> userLanguage = new ObservableField<>();//接触语种
    public final ObservableInt exitVisible = new ObservableInt();//退出登录按钮的显示
    public final ObservableInt topVisible = new ObservableInt();//android 5.0以上显示，否则不显示

    private Intent intent;
    @WatchThis
    public final BaseObservable onLogout = new BaseObservable();

    public UserCenterFragmentViewModel() {
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Account.preference().isLogin()) {
            getUserDes(Account.preference().getPhone());
            setUserInformation();
        } else {
            exitVisible.set(View.INVISIBLE);
            userName.set("");
        }
    }

    /**
     * 数据统计
     *
     * @param cardNo
     */
    private void getUserDes(String cardNo) {
        new StatisticsCardNoModel().getStatistics(cardNo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    if ("0".equals(apiResult.getStatus())) {
                        userTime.set(apiResult.getContent().getUseTime());
                        userNum.set(apiResult.getContent().getCallFrequency());
                        userLanguage.set(apiResult.getContent().getLanguages());
                    }
                });
    }

    private void setUserInformation() {
        userName.set(Account.preference().getPhone());//更新昵称
        exitVisible.set(View.VISIBLE);
    }


    private void init() {
        userName.set("");
        userTime.set("-- 分钟");
        userNum.set("-- 次");
        userLanguage.set("-- 种");
        exitVisible.set(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topVisible.set(View.VISIBLE);
        } else {
            topVisible.set(View.GONE);
        }
    }

    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.user_center_settting:
                intent = new Intent("com.lyun.user.intent.action.USER_SETTING");
                getActivity().startActivity(intent);
                break;
            case R.id.user_center_avatar:
                //  if(!Account.preference().isLogin())
                //  getActivity().startActivity(new Intent("com.lyun.user.intent.action.LOGIN"));
                break;
            case R.id.user_center_wallet:
                intent = new Intent("com.lyun.user.intent.action.WALLET_MAIN");
                getActivity().startActivity(intent);

                break;
            case R.id.user_center_exit:
                exit();
                break;
            case R.id.user_center_name:
                break;
            default:
                break;
        }
    }

    private void login(View view) {
        getActivity().startActivity(new Intent("com.lyun.user.intent.action.LOGIN"));
//        if (viewModel == null)
//            viewModel = new SimpleDialogViewModel(view.getContext());
//        viewModel.setYesBtnText("是");
//        viewModel.setCancelBtnText("否");
//        viewModel.setOnItemClickListener(new SimpleDialogViewModel.OnItemClickListener() {
//            @Override
//            public void OnYesClick(View view) {
//                getToast().show("yes");
//            }
//
//            @Override
//            public void OnCancelClick(View view) {
//              getToast().show("no");
//            }
//        });
//        viewModel.show();
    }

    private void exit() {
        Account.preference().clear();
        onLogout.notifyChange();
    }

}
