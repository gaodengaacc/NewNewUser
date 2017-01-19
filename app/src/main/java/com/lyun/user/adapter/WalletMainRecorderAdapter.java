package com.lyun.user.adapter;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.user.databinding.WalletMainRecorderItemBinding;
import com.lyun.user.viewmodel.WalletMainRecorderItemViewModel;

import java.util.List;

/**
 * @author Gordon
 * @since 2017/1/9
 * do()
 */

public class WalletMainRecorderAdapter extends BaseRecyclerAdapter<WalletMainRecorderItemBinding, WalletMainRecorderItemViewModel> {
    public WalletMainRecorderAdapter(List<WalletMainRecorderItemViewModel> viewModels, int layoutId) {
        super(viewModels, layoutId);
    }
    @Override
    public void viewBind(WalletMainRecorderItemViewModel viewModel, WalletMainRecorderItemBinding viewDataBinding, int position) {
        viewModel.init(position);
        viewDataBinding.setItemData(viewModel);
    }
}
