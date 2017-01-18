package com.lyun.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyun.library.mvvm.InterfaceBindView;
import com.lyun.library.mvvm.OnRecycleItemClickListener;
import com.lyun.library.mvvm.viewmodel.ViewModel;

import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/21
 * do()
 */

public abstract class BaseRecyclerAdapter<DB extends ViewDataBinding,VM extends ViewModel> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseRecyclerHolder> implements InterfaceBindView<DB,VM> {
    public List<VM> viewModels;
    public int layoutId;

    private OnRecycleItemClickListener itemClickListener;
    private OnRecycleItemClickListener itemLongClickListener;

    public BaseRecyclerAdapter(List<VM> viewModels, int layoutId) {
        this.viewModels = viewModels;
        this.layoutId = layoutId;
    }
    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DB dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
        return new BaseRecyclerHolder<DB>(dataBinding.getRoot(), dataBinding);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, final int position) {
        if(viewModels !=null){
            viewBind(viewModels.get(position), (DB) holder.getViewDataBinding(),position);
            if(itemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(v, viewModels,position);
                    }
                });
            }
            if(itemLongClickListener!=null)
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemLongClickListener.onItemClick(v, viewModels,position);
                    return false;
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return viewModels ==null ? 0: viewModels.size();
    }

    public class BaseRecyclerHolder<DB extends ViewDataBinding> extends RecyclerView.ViewHolder {

        public DB getViewDataBinding() {
            return viewDataBinding;
        }

        private DB viewDataBinding;

        public BaseRecyclerHolder(View itemView, DB dataBinding) {
            super(itemView);
            this.viewDataBinding = dataBinding;
        }

    }

    public void setItemClickListener(OnRecycleItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public void setItemLongClickListener(OnRecycleItemClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }
    public void setListData(List listData) {
        this.viewModels = listData;
        notifyDataSetChanged();
    }
}
