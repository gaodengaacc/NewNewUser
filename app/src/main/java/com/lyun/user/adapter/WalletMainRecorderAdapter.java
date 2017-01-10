package com.lyun.user.adapter;

import android.content.Context;

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


    public WalletMainRecorderAdapter(Context context, List<WalletMainRecorderItemViewModel> viewModels, int layoutId) {
        super(context, viewModels, layoutId);
    }

    @Override
    public void viewBind(WalletMainRecorderItemViewModel viewModel, WalletMainRecorderItemBinding viewDataBinding, int position) {
        viewModel.init(position);
        viewDataBinding.setItemData(viewModel);
    }
}
