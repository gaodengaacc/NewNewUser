package com.lyun.user.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyun.user.R;
import com.lyun.user.databinding.ItemDiscoverRecyclerviewBinding;
import com.lyun.user.model.DiscoverFragmentItemModel;
import com.lyun.user.viewmodel.DiscoverRecyclerItemViewModel;
import com.lyun.utils.ToastUtil;

import java.util.List;

/**
 * Created by 郑成裕 on 2016/12/16.
 */

public class DiscoverRecyclerAdapter extends RecyclerView.Adapter<DiscoverRecyclerAdapter.MyViewHolder> {
    private Context mContext;

    private List<DiscoverRecyclerItemViewModel> listData;
    private LayoutInflater mLayoutInflater;
    private DiscoverFragmentItemModel itemModel;
    public DiscoverRecyclerAdapter(Context mContext, List<DiscoverRecyclerItemViewModel> listData) {
        this.mContext = mContext;
        this.listData = listData;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    public void setListData(List<DiscoverRecyclerItemViewModel> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDiscoverRecyclerviewBinding viewBinding = DataBindingUtil.inflate(mLayoutInflater,R.layout.item_discover_recyclerview, parent, false);
        return new MyViewHolder(viewBinding.getRoot(),viewBinding);
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtil.show(mContext,"点击Item");
//            }
//        });
        holder.bind(listData.get(position));
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemDiscoverRecyclerviewBinding viewBinding;
        public MyViewHolder(View itemView, ViewDataBinding viewDataBinding) {
            super(itemView);
            this.viewBinding = (ItemDiscoverRecyclerviewBinding) viewDataBinding;
        }
        private void bind(DiscoverRecyclerItemViewModel viewModel) {
            itemModel = new DiscoverFragmentItemModel();
            itemModel.setViewModel(viewModel);
            itemModel.doData();
            viewBinding.setItemData(viewModel);
            viewBinding.setItemModel(itemModel);
        }
    }
}
