package com.lyun.user.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.user.databinding.ItemDiscoverRecyclerviewBinding;
import com.lyun.user.model.DiscoverFragmentItemModel;
import com.lyun.user.viewmodel.DiscoverRecyclerItemViewModel;
import com.lyun.viewmodel.BaseViewModel;

import java.util.List;

/**
 * Created by 郑成裕 on 2016/12/16.
 */

public class DiscoverRecyclerAdapter extends BaseRecyclerAdapter {
    private DiscoverFragmentItemModel itemModel;

    public DiscoverRecyclerAdapter(Context mContext, List listData, int layoutId) {
        super(mContext, listData, layoutId);
    }
    @Override
    public void viewBind(BaseViewModel baseViewModel, ViewDataBinding viewDataBinding) {
        itemModel = new DiscoverFragmentItemModel(context, baseViewModel);
        itemModel.doData();
        ItemDiscoverRecyclerviewBinding binding = (ItemDiscoverRecyclerviewBinding) viewDataBinding;
        binding.setItemData((DiscoverRecyclerItemViewModel) baseViewModel);
        binding.setItemModel(itemModel);
    }

}
