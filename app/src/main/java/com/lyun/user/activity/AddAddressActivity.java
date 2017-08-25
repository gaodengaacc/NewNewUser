package com.lyun.user.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.SimpleDialogViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityAddAddressBinding;
import com.lyun.user.eventbusmessage.EventActivityFinishMessage;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.eventbusmessage.address.EventAddressSelectMessage;
import com.lyun.user.eventbusmessage.address.EventCityPickDialogShowMessage;
import com.lyun.user.viewmodel.AddAddressViewModel;
import com.smarttop.library.widget.AddressSelectDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * @author Gordon
 * @since 2017/8/23
 * do()
 */

public class AddAddressActivity extends GeneralToolbarActivity<ActivityAddAddressBinding, AddAddressViewModel> {
    private SimpleDialogViewModel simpleDialogViewModel;
    private AddressSelectDialog selectDialog;

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_add_address;
    }

    @NonNull
    @Override
    protected AddAddressViewModel createBodyViewModel() {
        return new AddAddressViewModel(getIntent().getExtras());
    }

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
        if (getIntent().getBooleanExtra("isEditor", false)) {
            viewModel.title.set("修改收件地址");
            viewModel.function.set("删除");
            viewModel.onFunctionClick.set(v -> {
                showDeleteDialog();
            });
        } else
            viewModel.title.set("新建收件地址");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }

    @Subscribe
    public void showProgress(EventProgressMessage message) {

    }

    @Subscribe
    public void showToast(EventToastMessage message) {

    }

    @Subscribe
    public void finish(EventActivityFinishMessage message) {
        if (message.getMessage())
            finish();
    }

    @Subscribe
    public void showCityPickDialog(EventCityPickDialogShowMessage message) {
        if (selectDialog == null) {
            selectDialog = new AddressSelectDialog(this);
            selectDialog.setOnAddressSelectedListener((province, city, county, street)
                    -> {
                StringBuffer address = new StringBuffer();
                if (province != null) address.append(province.name);
                if (city != null) address.append(city.name);
                if (county != null) address.append(county.name);
                if (street != null) address.append(street.name);
                getBodyViewModel().address.set(address.toString());
            });
        }
        selectDialog.show();
    }

    public void showDeleteDialog() {
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
                    EventBus.getDefault().post(new EventAddressSelectMessage(getBodyViewModel().position, 2));
                    finish();
                }
            });
        }
        simpleDialogViewModel.show();
    }
}
