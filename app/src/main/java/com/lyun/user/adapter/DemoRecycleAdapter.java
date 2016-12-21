package com.lyun.user.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyun.user.R;
import com.lyun.user.databinding.ItemDemoBinding;
import com.lyun.user.model.DemoItemModel;
import com.lyun.user.viewmodel.DemoItemViewModel;

import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/16
 * do()
 */
public class DemoRecycleAdapter extends RecyclerView.Adapter<DemoRecycleAdapter.DemoRecyclerHolder> {
    private LayoutInflater m_layoutInflater;
    private List<DemoItemViewModel> m_demoItemViewModels;
    private DemoItemModel demoItemModel;
    public DemoRecycleAdapter(Context context, List<DemoItemViewModel> demoItemViewModels) {
        m_layoutInflater = LayoutInflater.from(context);
        m_demoItemViewModels = demoItemViewModels;
        demoItemModel = new DemoItemModel();
    }

    @Override
    public DemoRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(m_layoutInflater, R.layout.item_demo, parent, false);
        return new DemoRecyclerHolder(viewDataBinding.getRoot(), viewDataBinding);
    }

    @Override
    public void onBindViewHolder(DemoRecyclerHolder holder, int position) {
        final int currentPosition = holder.getLayoutPosition();
        holder.bind(m_demoItemViewModels.get(currentPosition));
    }

    @Override
    public int getItemCount() {
        return m_demoItemViewModels.size();
    }

    /**
     * Created by chan on 16/3/12.
     */
    public class DemoRecyclerHolder extends RecyclerView.ViewHolder {
      ItemDemoBinding m_itemLayoutBinding;

        public DemoRecyclerHolder(View itemView, ViewDataBinding viewDataBinding) {
            super(itemView);
            m_itemLayoutBinding = (ItemDemoBinding) viewDataBinding;
        }

        public void bind(DemoItemViewModel demoItemViewModel) {
            demoItemModel = new DemoItemModel();
            demoItemModel.setDemoItemViewModel(demoItemViewModel);
            demoItemModel.doData();
            m_itemLayoutBinding.setData(demoItemViewModel);
//            m_itemLayoutBinding.setVariable(BR.data, demoItemViewModel);
//            m_itemLayoutBinding.executePendingBindings();
        }

    }
}
