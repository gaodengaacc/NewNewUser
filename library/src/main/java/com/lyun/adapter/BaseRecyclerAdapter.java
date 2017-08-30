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

public abstract class BaseRecyclerAdapter<DB extends ViewDataBinding, VM extends ViewModel> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseRecyclerHolder> implements InterfaceBindView<DB, VM> {
    public List<VM> viewModels;
    public int layoutId;
    private View mHeaderView;
    private View mFooterView;
    private OnRecycleItemClickListener itemClickListener;
    private OnRecycleItemClickListener itemLongClickListener;
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    private int newPosition;

    public BaseRecyclerAdapter() {
        this(null, 0);
    }

    public BaseRecyclerAdapter(List<VM> viewModels, int layoutId) {
        this.viewModels = viewModels;
        this.layoutId = layoutId;
    }

    public void setViewModels(List<VM> viewModels){
        this.viewModels = viewModels;
    }

    public void setLayoutId(int layoutId){
        this.layoutId = layoutId;
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new BaseRecyclerHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new BaseRecyclerHolder(mFooterView);
        }
        DB dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
        return new BaseRecyclerHolder<DB>(dataBinding.getRoot(), dataBinding);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, final int position) {
        if (viewModels != null) {
            if (getItemViewType(position) == TYPE_NORMAL) {
                if (mHeaderView != null)
                    newPosition = position - 1 < 0 ? 0 : position - 1;
                else
                    newPosition = position;
                viewBind(viewModels.get(newPosition), (DB) holder.getViewDataBinding(), newPosition);
                if (itemClickListener != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mHeaderView != null)
                                newPosition = position - 1 < 0 ? 0 : position - 1;
                            else
                                newPosition = position;
                            itemClickListener.onItemClick(v, viewModels, newPosition);
                        }
                    });
                }
                if (itemLongClickListener != null)
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (mHeaderView != null)
                                newPosition = position - 1 < 0 ? 0 : position - 1;
                            else
                                newPosition = position;
                            itemLongClickListener.onItemClick(v, viewModels, newPosition);
                            return false;
                        }
                    });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0 && mHeaderView != null) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1 && mFooterView != null) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return viewModels.size();
        } else if (mHeaderView == null && mFooterView != null) {
            return viewModels.size() + 1;
        } else if (mHeaderView != null && mFooterView == null) {
            return viewModels.size() + 1;
        } else {
            return viewModels.size() + 2;
        }
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

        public BaseRecyclerHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
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
