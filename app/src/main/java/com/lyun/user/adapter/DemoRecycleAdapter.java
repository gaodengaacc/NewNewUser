package com.lyun.user.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.user.databinding.ItemDemoBinding;
import com.lyun.user.model.DemoItemModel;
import com.lyun.user.viewmodel.DemoItemViewModel;
import com.lyun.viewmodel.BaseViewModel;

import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/16
 * do()
 */
public class DemoRecycleAdapter extends BaseRecyclerAdapter {
    private DemoItemModel demoItemModel;
    public DemoRecycleAdapter(Context context, List demoItemViewModels, int itemLayoutId) {
        super(context, demoItemViewModels, itemLayoutId);
    }
    @Override
    public void viewBind(BaseViewModel baseViewModel, ViewDataBinding dataBinding) {
        demoItemModel = new DemoItemModel(context, baseViewModel);
        demoItemModel.doData();
        ItemDemoBinding binding= (ItemDemoBinding) dataBinding;
        binding.setData((DemoItemViewModel) baseViewModel);
        binding.setModel(demoItemModel);
    }
}
