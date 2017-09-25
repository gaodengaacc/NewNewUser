package com.lyun.user.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.R;
import com.lyun.user.api.response.AddressItemResponse;
import com.lyun.user.databinding.ActivityApplyForInvoiceBinding;
import com.lyun.user.viewmodel.ApplyForInvoiceViewModel;
import com.lyun.user.viewmodel.watchdog.IApplyForInvoiceViewModelCallbacks;
import com.lyun.utils.ToastUtil;

import static com.lyun.user.activity.AddressManageActivity.EXTRA_CHOOSE_ADDRESS;

public class ApplyForInvoiceActivity extends GeneralToolbarActivity<ActivityApplyForInvoiceBinding, ApplyForInvoiceViewModel>
        implements IApplyForInvoiceViewModelCallbacks {

    public final int REQUEST_CODE_CHOOSE_ADDRESS = 0x001;

    public static final String EXTRA_ORDER_NO = "orderNo";

    private String orderNo;

    public static void start(Context context, String orderNo) {
        Intent intent = new Intent(context, ApplyForInvoiceActivity.class);
        intent.putExtra(EXTRA_ORDER_NO, orderNo);
        context.startActivity(intent);
    }

    public static void startForResult(Activity context, String orderNo, int requestCode) {
        Intent intent = new Intent(context, ApplyForInvoiceActivity.class);
        intent.putExtra(EXTRA_ORDER_NO, orderNo);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        orderNo = getIntent().getStringExtra(EXTRA_ORDER_NO);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_apply_for_invoice;
    }

    @NonNull
    @Override
    protected ApplyForInvoiceViewModel createBodyViewModel() {
        return new ApplyForInvoiceViewModel(orderNo).setPropertyChangeListener(this);
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel = super.createTitleViewModel();
        toolbarViewModel.onBackClick.set(v -> finish());
        toolbarViewModel.title.set("申请发票");
        return toolbarViewModel;
    }

    @Override
    public void onChooseAddress(ObservableInt observableField, int fieldId) {
        Intent intent = new Intent(this, AddressManageActivity.class);
        intent.putExtra(EXTRA_CHOOSE_ADDRESS, true);
        startActivityForResult(intent, REQUEST_CODE_CHOOSE_ADDRESS);
    }

    @Override
    public void applyForInvoiceSuccess(ObservableInt observableField, int fieldId) {
        ToastUtil.show(this, "发票申请成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_ADDRESS && resultCode == RESULT_OK) {
            getBodyViewModel().setAddress((AddressItemResponse) data.getSerializableExtra(AddressManageActivity.EXTRA_ADDRESS));
        }
    }
}
