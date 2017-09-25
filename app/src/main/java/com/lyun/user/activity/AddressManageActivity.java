package com.lyun.user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.SimpleDialogViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityAddressManageBinding;
import com.lyun.user.eventbusmessage.EventIntentActivityMessage;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.eventbusmessage.address.EventAddressSelectMessage;
import com.lyun.user.viewmodel.AddressManageViewModel;
import com.lyun.utils.TipsToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author Gordon
 * @since 2017/8/22
 * do()
 */

public class AddressManageActivity extends GeneralToolbarActivity<ActivityAddressManageBinding, AddressManageViewModel> {

    public static final String EXTRA_ADDRESS = "address";
    public static final String EXTRA_CHOOSE_ADDRESS = "chooseAddress";

    private boolean isChooseAddress = false;

    private Toast toast;
    private SimpleDialogViewModel simpleDialogViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        isChooseAddress = getIntent().getBooleanExtra(EXTRA_CHOOSE_ADDRESS, false);

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
        dialogViewModel.show();
        return new AddressManageViewModel();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showProgress(EventProgressMessage message) {
        if (message.getMessage())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showToast(EventToastMessage message) {
        if (message.getMessage() != null && !message.getMessage().equals(""))
            if (message.getMessage().equals("保存成功")) {
                TipsToast tipsToast = TipsToast.makeText(getBaseContext(), message.getMessage(), TipsToast.LENGTH_SHORT);
                tipsToast.setIcon(R.mipmap.icon_address_success);
                tipsToast.show();
            } else {
                if (toast == null)
                    toast = Toast.makeText(getBaseContext(), message.getMessage(), Toast.LENGTH_SHORT);
                toast.setText(message.getMessage());
                toast.show();
            }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setAddress(EventAddressSelectMessage message) {
        Intent intent;
        switch (message.getAction()) {//0：设置默认地址 1：编辑 2：删除 3:更新 4:添加 5:删除回调 6:选择地址
            case 0:
                getBodyViewModel().setDefaultAddress(message.getMessage());
                break;
            case 1:
                Bundle bundle = new Bundle();
                bundle.putInt("position", message.getMessage());
                bundle.putSerializable("response", getBodyViewModel().itemViewModelData.get(message.getMessage()).response);
                intent = new Intent();
                intent.putExtra("isEditor", true);
                intent.putExtras(bundle);
                EventBus.getDefault().post(new EventIntentActivityMessage(intent));
                break;
            case 2:
//                getBodyViewModel().deleteAddress(message.getMessage(), false);
                showDeleteDialog(message.getMessage());
                break;
            case 3:
                getBodyViewModel().updateAddress(message.getMessage(), message.getResponse());
                break;
            case 4:
                getBodyViewModel().AddAddress(message.getResponse());
                break;
            case 5:
                getBodyViewModel().deleteAddress(message.getMessage(), true);
                break;
            case 6:
                if(isChooseAddress) {
                    intent = new Intent();
                    intent.putExtra(EXTRA_ADDRESS, message.getResponse());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            default:
                break;

        }

    }

    @Subscribe
    public void toAddActivity(EventIntentActivityMessage message) {
        startActivity(message.getMessage().setClass(this, AddAddressActivity.class));
    }

    public void showDeleteDialog(final int position) {
        if (simpleDialogViewModel == null) {
            simpleDialogViewModel = new SimpleDialogViewModel(this);
            simpleDialogViewModel.setInfo("确认删除地址吗？");
            simpleDialogViewModel.setCancelBtnText("是");
            simpleDialogViewModel.setYesBtnText("否");
            simpleDialogViewModel.setBtnCancelTextColor(Color.parseColor("#fe811c"));
            simpleDialogViewModel.setOnItemClickListener(new SimpleDialogViewModel.OnItemClickListener() {
                @Override
                public void OnYesClick(View view) {

                }

                @Override
                public void OnCancelClick(View view) {
                    getBodyViewModel().deleteAddress(position, false);
                }
            });
        }
        simpleDialogViewModel.show();
    }
}
