package com.lyun.user.model;

import android.content.Context;
import android.view.View;

import com.lyun.model.BaseModel;
import com.lyun.user.viewmodel.DiscoverRecyclerItemViewModel;
import com.lyun.utils.ToastUtil;
import com.lyun.viewmodel.BaseViewModel;

/**
 * @author Gordon
 * @since 2016/12/20
 * do(item业务处理)
 */

public class DiscoverFragmentItemModel extends BaseModel {
    private DiscoverRecyclerItemViewModel viewModel;

    public DiscoverFragmentItemModel(Context context, BaseViewModel viewModel) {
        super(context, viewModel);
        this.viewModel = (DiscoverRecyclerItemViewModel) viewModel;
    }

    @Override
    public void init() {

    }

    public void doData(){

    }
    public void onItemImageOnClick(View view){
        viewModel.setListTitle("图片被点击");
        ToastUtil.show(view.getContext(),"点击图片");
    }
}
