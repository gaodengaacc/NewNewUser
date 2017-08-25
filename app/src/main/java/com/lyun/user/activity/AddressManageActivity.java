package com.lyun.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityAddressManageBinding;
import com.lyun.user.eventbusmessage.EventIntentActivityMessage;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.address.EventAddressSelectMessage;
import com.lyun.user.viewmodel.AddressManageViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Gordon
 * @since 2017/8/22
 * do()
 */

public class AddressManageActivity extends GeneralToolbarActivity<ActivityAddressManageBinding, AddressManageViewModel> {
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
        viewModel.title.set("地址管理");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_address_manage;
    }

    @NonNull
    @Override
    protected AddressManageViewModel createBodyViewModel() {
        return new AddressManageViewModel();
    }

    @Subscribe
    public void showProgress(EventProgressMessage message) {
        if (message.getMessage())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }

    @Subscribe
    public void setDefaultAddress(EventAddressSelectMessage message) {
        switch (message.getAction()) {//0：设置默认地址 1：编辑 2：删除 3:更新
            case 0:
                getBodyViewModel().setDefaultAddress(message.getMessage());
                break;
            case 1:
                Bundle bundle = new Bundle();
                bundle.putInt("position",message.getMessage());
                bundle.putSerializable("response", getBodyViewModel().itemViewModelData.get(message.getMessage()).response);
                Intent intent = new Intent();
                intent.putExtra("isEditor", true);
                intent.putExtras(bundle);
                EventBus.getDefault().post(new EventIntentActivityMessage(intent));
                break;
            case 2:
                getBodyViewModel().deleteAddress(message.getMessage());
                break;
            case 3:
                getBodyViewModel().updateAddress(message.getMessage(),message.getResponse());
                break;
            default:
                break;

        }

    }

    @Subscribe
    public void toAddActivity(EventIntentActivityMessage message) {
        startActivity(message.getMessage().setClass(this, AddAddressActivity.class));
    }
}
