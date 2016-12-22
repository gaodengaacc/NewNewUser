package com.lyun.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyun.viewmodel.BaseViewModel;
import com.lyun.viewmodel.InterfaceBindView;
import com.lyun.viewmodel.OnRecycleItemClickListener;

import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/21
 * do()
 */

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseRecyclerHolder> implements InterfaceBindView {
    public LayoutInflater layoutInflater;
    public List<BaseViewModel> baseViewModels;
    public Context context;
    public int layoutId;

    private OnRecycleItemClickListener itemClickListener;

    public BaseRecyclerAdapter(Context context, List<BaseViewModel> baseViewModels, int layoutId) {
        this.context = context;
        this.baseViewModels = baseViewModels;
        layoutInflater = LayoutInflater.from(context);
        this.layoutId = layoutId;

    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(layoutInflater, layoutId, parent, false);
        return new BaseRecyclerHolder(dataBinding.getRoot(), dataBinding);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, final int position) {
        viewBind(baseViewModels.get(position), holder.getViewDataBinding());
        if(itemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      itemClickListener.onItemClick(v,baseViewModels,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return baseViewModels.size();
    }

    public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

        public ViewDataBinding getViewDataBinding() {
            return viewDataBinding;
        }

        private ViewDataBinding viewDataBinding;

        public BaseRecyclerHolder(View itemView, ViewDataBinding dataBinding) {
            super(itemView);
            this.viewDataBinding = dataBinding;
        }

    }

    public void setItemClickListener(OnRecycleItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setListData(List listData) {
        this.baseViewModels = listData;
        notifyDataSetChanged();
    }
}
