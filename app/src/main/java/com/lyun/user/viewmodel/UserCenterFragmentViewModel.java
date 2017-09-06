package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Build;
import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.AppIntent;
import com.lyun.user.Constants;
import com.lyun.user.R;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.eventbusmessage.homefragment.EventMainIntentActivityMessage;
import com.lyun.user.model.StatisticsCardNoModel;
import com.lyun.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 郑成裕 on 2016/12/28.
 */

public class UserCenterFragmentViewModel extends ViewModel {

    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> userTime = new ObservableField<>();//使用时长
    public final ObservableField<String> userNum = new ObservableField<>();//使用次数
    public final ObservableField<String> userLanguage = new ObservableField<>();//接触语种
    public final ObservableInt topVisible = new ObservableInt();//android 5.0以上显示，否则不显示
    public final ObservableField<String> avatar = new ObservableField<String>();

    private Intent intent;

    public UserCenterFragmentViewModel() {
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Account.preference().isLogin()) {
            getUserDes(Account.preference().getUserId());//获取数据统计
            setUserInformation();
        } else {
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
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    if (apiResult.isSuccess()) {//获取成功
                        userTime.set(TimeUtil.convertMin2Str(apiResult.getContent().getUseTime()));
                        userNum.set(apiResult.getContent().getCallFrequency()+"次");
                        userLanguage.set(apiResult.getContent().getDomains() + "种");
                    } else {
                        EventBus.getDefault().post(new EventToastMessage(apiResult.getDescribe()));
                    }
                }, throwable -> throwable.printStackTrace());
    }

    private void setUserInformation() {
        userName.set(hideUserName(Account.preference().getUserId()));//更新昵称
    }

    private String hideUserName(String phone) {
        try {
            return phone.substring(0, 3) + "*****" + phone.substring(8);
        } catch (Exception e) {
            return phone;
        }
    }

    private void init() {
        userName.set("");
        userTime.set("-- 分钟");
        userNum.set("-- 次");
        userLanguage.set("-- 种");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topVisible.set(View.VISIBLE);
        } else {
            topVisible.set(View.GONE);
        }
        avatar.set(Constants.IMAGE_BASE_URL + Account.preference().getUserHeader());
    }

    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.user_center_settting_layout:
                intent = new Intent(AppIntent.ACTION_SETTINGS);
                getActivity().startActivity(intent);
                break;
            case R.id.user_center_avatar:
                intent = new Intent();
                intent.putExtra("flag","ImageCropActivity");
                EventBus.getDefault().post(new EventMainIntentActivityMessage(intent));
                break;
            case R.id.user_center_account_layout:
                intent = new Intent();
                intent.putExtra("flag","AccountBindingActivity");
                EventBus.getDefault().post(new EventMainIntentActivityMessage(intent));
                break;
            case R.id.user_center_card_layout:
                intent = new Intent();
                intent.putExtra("flag","UserServiceCardListActivity");
                EventBus.getDefault().post(new EventMainIntentActivityMessage(intent));
                break;
            case R.id.user_center_address_layout:
                intent = new Intent();
                intent.putExtra("flag","AddressManageActivity");
                EventBus.getDefault().post(new EventMainIntentActivityMessage(intent));
                break;
            case R.id.user_center_name:
                break;
            case R.id.user_center_service_layout:
                intent = new Intent();
                intent.putExtra("flag","AfterSaleServiceActivity");
                EventBus.getDefault().post(new EventMainIntentActivityMessage(intent));
                break;
            default:
                break;
        }
    }
    private void exit() {
        Account.preference().clear();
    }

}
