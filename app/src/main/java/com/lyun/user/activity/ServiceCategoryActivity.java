package com.lyun.user.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityServiceCategoryBinding;
import com.lyun.user.viewmodel.ServiceCategoryActivityViewModel;

import butterknife.OnClick;

/**
 * Created by 郑成裕 on 2016/12/21.
 */

public class ServiceCategoryActivity extends GeneralToolbarActivity<ActivityServiceCategoryBinding, ServiceCategoryActivityViewModel> implements View.OnClickListener {

    Intent intent1;
    String languageCategory;

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_service_category;
    }

    @NonNull
    @Override
    protected ServiceCategoryActivityViewModel createBodyViewModel() {
        intent1 = this.getIntent();
        languageCategory = intent1.getStringExtra("languageCategory");
        ServiceCategoryActivityViewModel serviceCategoryActivityViewModel = new ServiceCategoryActivityViewModel(this, getTitleViewDataBinding().getMvvm(), languageCategory);

        return serviceCategoryActivityViewModel;
    }

    @Override
    @OnClick({R.id.relativeLayout_normalService, R.id.relativeLayout_travel, R.id.relativeLayout_hotel, R.id.relativeLayout_shopping, R.id.relativeLayout_eat, R.id.relativeLayout_urgency})
    public void onClick(View view) {
    }
}
