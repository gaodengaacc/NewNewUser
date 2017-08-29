package com.lyun.user.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityApplyForInvoiceBinding;
import com.lyun.user.viewmodel.ApplyForInvoiceViewModel;

public class ApplyForInvoiceActivity extends GeneralToolbarActivity<ActivityApplyForInvoiceBinding, ApplyForInvoiceViewModel> {

    public static void start(Context context) {
        context.startActivity(new Intent(context, ApplyForInvoiceActivity.class));
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_apply_for_invoice;
    }

    @NonNull
    @Override
    protected ApplyForInvoiceViewModel createBodyViewModel() {
        return new ApplyForInvoiceViewModel().setPropertyChangeListener(this);
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel = super.createTitleViewModel();
        toolbarViewModel.onBackClick.set(v -> finish());
        toolbarViewModel.title.set("申请发票");
        return toolbarViewModel;
    }
}
