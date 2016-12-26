package com.lyun.user.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.databinding.ItemDiscoverRecyclerviewBinding;
import com.lyun.user.fragment.DiscoverFragment;
import com.lyun.user.viewmodel.DiscoverRecyclerItemViewModel;
import com.lyun.viewmodel.BaseViewModel;

import java.util.List;

/**
 * Created by 郑成裕 on 2016/12/16.
 */

public class DiscoverRecyclerAdapter extends BaseRecyclerAdapter {

    public DiscoverRecyclerAdapter(Context mContext, List listData, int layoutId) {
        super(mContext, listData, layoutId);
    }
    @Override
    public void viewBind(ViewModel baseViewModel, ViewDataBinding viewDataBinding,int position) {
        DiscoverRecyclerItemViewModel itemModel = (DiscoverRecyclerItemViewModel) baseViewModel;
        itemModel.init(position);
        ItemDiscoverRecyclerviewBinding binding = (ItemDiscoverRecyclerviewBinding) viewDataBinding;
        binding.setItemData(itemModel);
    }

}
