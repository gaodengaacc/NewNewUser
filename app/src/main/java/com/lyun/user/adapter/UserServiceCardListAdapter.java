package com.lyun.user.adapter;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.user.databinding.ItemUserServiceCardLayoutBinding;
import com.lyun.user.viewmodel.ServiceCardItemViewModel;

import java.util.List;

/**
 * @author Gordon
 * @since 2017/8/2
 * do()
 */
public class UserServiceCardListAdapter extends BaseRecyclerAdapter<ItemUserServiceCardLayoutBinding, ServiceCardItemViewModel> {
    public UserServiceCardListAdapter(List<ServiceCardItemViewModel> viewModels, int layoutId) {
        super(viewModels, layoutId);
    }

    @Override
    public void viewBind(ServiceCardItemViewModel viewModel, ItemUserServiceCardLayoutBinding viewDataBinding, int position) {
        viewDataBinding.setMvvm(viewModel);
    }
}