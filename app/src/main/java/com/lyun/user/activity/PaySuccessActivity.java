package com.lyun.user.activity;

import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityPaySuccessBinding;
import com.lyun.user.fragment.ServiceCardFragment;
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
        return new PaySuccessViewModel((ServiceCardFragment.PaySuccessInfo) getIntent().getSerializableExtra("paySuccessInfo"));
    }
}
