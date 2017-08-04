package com.lyun.user.activity;

import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityCardTasteLayoutBinding;
import com.lyun.user.viewmodel.ServiceCardTasteViewModel;

/**
 * @author Gordon
 * @since 2017/8/1
 * do()
 */

public class ServiceCardTasteDetailActivity extends GeneralToolbarActivity<ActivityCardTasteLayoutBinding, ServiceCardTasteViewModel> {
    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_card_taste_layout;
    }

    @NonNull
    @Override
    protected ServiceCardTasteViewModel createBodyViewModel() {
        return new ServiceCardTasteViewModel();
    }
    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel viewModel = super.createTitleViewModel();
        viewModel.setPropertyChangeListener(this);
        viewModel.title.set("服务卡详情");
        viewModel.onBackClick.set(view -> finish());
        return viewModel;
    }
}
