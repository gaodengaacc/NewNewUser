package com.lyun.user.model;

import android.view.View;

import com.lyun.user.viewmodel.DiscoverRecyclerItemViewModel;
import com.lyun.utils.ToastUtil;

/**
 * @author Gordon
 * @since 2016/12/20
 * do(item业务处理)
 */

public class DiscoverFragmentItemModel {
    public void setViewModel(DiscoverRecyclerItemViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private DiscoverRecyclerItemViewModel viewModel;
    public DiscoverFragmentItemModel(){

    }
    public void doData(){

    }
    public void onItemImageOnClick(View view){
        viewModel.setListTitle("图片被点击");
        ToastUtil.show(view.getContext(),"点击图片");
    }
}
