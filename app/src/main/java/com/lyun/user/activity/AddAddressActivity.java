package com.lyun.user.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.SimpleDialogViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityAddAddressBinding;
import com.lyun.user.eventbusmessage.EventActivityFinishMessage;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.eventbusmessage.address.EventCityPickDialogShowMessage;
import com.lyun.user.viewmodel.AddAddressViewModel;
import com.smarttop.library.widget.AddressSelectDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * @author Gordon
 * @since 2017/8/23
 * do()
 */

public class AddAddressActivity extends GeneralToolbarActivity<ActivityAddAddressBinding, AddAddressViewModel> {
    private SimpleDialogViewModel simpleDialogViewModel;
    private AddressSelectDialog selectDialog;
    private String provinceBack;
    private String cityBack;
    private String districtBack;
    private String streetBack;
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
        Toast.makeText(getApplication(), message.getMessage(), Toast.LENGTH_LONG).show();
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
            selectDialog.setOnAddressSelectedListener((province, city, district, street)
                    -> {
                provinceBack = (province == null ? "" : province.name);
                cityBack = (city == null ? "" : city.name);
                districtBack = (district == null ? "" : district.name);
                streetBack = (street == null ? "" : street.name);
                if (province != null && Integer.parseInt(province.code) < 5) {
                    streetBack = districtBack;
                    districtBack = cityBack;
                    cityBack = provinceBack + "市";
                }
                getBodyViewModel().setAddress(provinceBack, cityBack, districtBack, streetBack);
                selectDialog.dismiss();
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
                    getBodyViewModel().deleteAddress();
                }
            });
        }
        simpleDialogViewModel.show();
    }
}
