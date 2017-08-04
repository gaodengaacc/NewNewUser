package com.lyun.user.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.R;
import com.lyun.user.api.response.ServiceCardListItemResponse;
import com.lyun.user.databinding.ActivityCardListLayoutBinding;
import com.lyun.user.eventbusmessage.EventListItemMessage;
import com.lyun.user.viewmodel.UserServiceCardListViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author Gordon
 * @since 2017/7/31
 * do()
 */

public class UserServiceCardListActivity extends GeneralToolbarActivity<ActivityCardListLayoutBinding,UserServiceCardListViewModel> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("我的服务卡");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }
    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_card_list_layout;
    }

    @NonNull
    @Override
    protected UserServiceCardListViewModel createBodyViewModel() {
        return new UserServiceCardListViewModel();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemClick(EventListItemMessage message){
        ServiceCardListItemResponse response = (ServiceCardListItemResponse) message.getMessage();

    }
}
