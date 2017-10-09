package com.lyun.user.activity;

import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityPaySuccessBinding;
import com.lyun.user.pay.PaySuccessInfo;
import com.lyun.user.viewmodel.PaySuccessViewModel;

/**
 * @author Gordon
 * @since 2017/9/29
 * do()
 */

public class PaySuccessActivity extends GeneralToolbarActivity<ActivityPaySuccessBinding,PaySuccessViewModel>{
    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_pay_success;
    }

    @NonNull
    @Override
    protected PaySuccessViewModel createBodyViewModel() {
        return new PaySuccessViewModel((PaySuccessInfo) getIntent().getSerializableExtra("paySuccessInfo"));
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("律云法律服务");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }
}
