package com.lyun.user.adapter;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.user.databinding.ItemServiceCardLayoutBinding;
import com.lyun.user.viewmodel.ServiceCardItemViewModel;

import java.util.List;

/**
 * @author Gordon
 * @since 2017/7/31
 * do()
 */

public class ServiceCardListAdapter extends BaseRecyclerAdapter<ItemServiceCardLayoutBinding, ServiceCardItemViewModel> {
    public ServiceCardListAdapter(List<ServiceCardItemViewModel> viewModels, int layoutId) {
        super(viewModels, layoutId);
    }

    @Override
    public void viewBind(ServiceCardItemViewModel viewModel, ItemServiceCardLayoutBinding viewDataBinding, int position) {
        viewDataBinding.setMvvm(viewModel);
    }
}
