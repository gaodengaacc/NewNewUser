package com.lyun.user.adapter;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.user.databinding.ItemWalletMainPopwindowBinding;
import com.lyun.user.viewmodel.WalletMainPopDesItemViewModel;

import java.util.List;

/**
 * @author Gordon
 * @since 2017/1/10
 * do()
 */

public class WalletMainPopAdapter extends BaseRecyclerAdapter<ItemWalletMainPopwindowBinding,WalletMainPopDesItemViewModel> {

    public WalletMainPopAdapter(List<WalletMainPopDesItemViewModel> viewModels, int layoutId) {
        super(viewModels, layoutId);
    }

    @Override
    public void viewBind(WalletMainPopDesItemViewModel viewModel, ItemWalletMainPopwindowBinding viewDataBinding, int position) {
        viewDataBinding.setMvvm(viewModel);
    }
}
