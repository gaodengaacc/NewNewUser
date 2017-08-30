package com.lyun.user.adapter;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.user.databinding.ItemServiceCardItemBinding;
import com.lyun.user.viewmodel.ServiceCardServiceItemViewModel;

import java.util.List;

/**
 * @author Gordon
 * @since 2017/7/31
 * do()
 */

public class ServiceCardListAdapter extends BaseRecyclerAdapter<ItemServiceCardItemBinding, ServiceCardServiceItemViewModel> {
    public ServiceCardListAdapter(List<ServiceCardServiceItemViewModel> viewModels, int layoutId) {
        super(viewModels, layoutId);
    }

    @Override
    public void viewBind(ServiceCardServiceItemViewModel viewModel, ItemServiceCardItemBinding viewDataBinding, int position) {
        viewDataBinding.setMvvm(viewModel);
    }
}
