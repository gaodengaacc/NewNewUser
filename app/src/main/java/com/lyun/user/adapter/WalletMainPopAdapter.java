package com.lyun.user.adapter;

import android.content.Context;

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

    public WalletMainPopAdapter(Context context, List<WalletMainPopDesItemViewModel> viewModels, int layoutId) {
        super(context, viewModels, layoutId);
    }

    @Override
    public void viewBind(WalletMainPopDesItemViewModel viewModel, ItemWalletMainPopwindowBinding viewDataBinding, int position) {
        viewDataBinding.setMvvm(viewModel);
    }
}
