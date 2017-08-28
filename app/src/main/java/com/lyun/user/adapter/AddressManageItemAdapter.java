package com.lyun.user.adapter;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.user.databinding.ItemAddressLayoutBinding;
import com.lyun.user.viewmodel.AddressManageItemViewModel;

import java.util.List;

/**
 * @author Gordon
 * @since 2017/8/22
 * do()
 */

public class AddressManageItemAdapter extends BaseRecyclerAdapter<ItemAddressLayoutBinding,AddressManageItemViewModel> {

    public AddressManageItemAdapter(List<AddressManageItemViewModel> viewModels, int layoutId) {
        super(viewModels, layoutId);
    }

    @Override
    public void viewBind(AddressManageItemViewModel viewModel, ItemAddressLayoutBinding viewDataBinding, int position) {
          viewModel.init(position);
          viewDataBinding.setMvvm(viewModel);
    }
}
