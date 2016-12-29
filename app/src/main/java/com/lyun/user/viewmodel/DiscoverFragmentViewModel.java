package com.lyun.user.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.adapter.DiscoverRecyclerAdapter;
import com.lyun.utils.ToastUtil;
import com.lyun.library.mvvm.OnRecycleItemClickListener;
import com.lyun.widget.dialog.ProgressDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/23
 * do()
 */

public class DiscoverFragmentViewModel extends ViewModel {
    public final ObservableField<List<DiscoverRecyclerItemViewModel>> notifyData = new ObservableField<>();
    public final ObservableField<BaseRecyclerAdapter> adapter = new ObservableField<>();
    private ProgressDialog progressDialog;

    public DiscoverFragmentViewModel(Context context) {
        super(context);
    }

    public void initData() {
        List<DiscoverRecyclerItemViewModel> list = new ArrayList<DiscoverRecyclerItemViewModel>();
        DiscoverRecyclerAdapter discoverRecyclerViewAdapter = new DiscoverRecyclerAdapter(getContext(), list, R.layout.item_discover_recyclerview);
        discoverRecyclerViewAdapter.setItemClickListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(View view, List<ViewModel> viewModels, int position) {
                DiscoverRecyclerItemViewModel itemViewModel = (DiscoverRecyclerItemViewModel) viewModels.get(position);
                ToastUtil.show(getContext(), itemViewModel.listTitle.get() + position);
//                activityFinish.set(true);
            }
        });
        adapter.set(discoverRecyclerViewAdapter);
    }

    public double divideWidth(int screenWidth, int picWidth, int retainValue) {
        BigDecimal screenBD = new BigDecimal(Double.toString(screenWidth));
        BigDecimal picBD = new BigDecimal(Double.toString(picWidth));
        return screenBD.divide(picBD, retainValue, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    public void moreBtnCLick(View view) {
        List<DiscoverRecyclerItemViewModel> list = new ArrayList<DiscoverRecyclerItemViewModel>();
        int[] imagesId = new int[]{R.mipmap.image1, R.mipmap.image2, R.mipmap.image3};
        for (int i = 0; i < 3; i++) {
            DiscoverRecyclerItemViewModel viewModel = new DiscoverRecyclerItemViewModel(getContext(), "新数据", "在英国可以感受西方文化,她,迷人又多面!", imagesId[i]);
            list.add(viewModel);
        }
        notifyData.set(list);
//        dialog = new ProgressBarDialog(context);
//        dialog.show();
//        progressDialog = new ProgressDialog(context);
//        progressDialog.show();
    }

    public void init() {
        initData();

//        discoverFragment.getDiscoverRecyclerViewAdapter().setItemClickListener(new OnRecycleItemClickListener() {
//            @Override
//            public void onItemClick(View view, List<ViewModel> viewModels, int position) {
//                ToastUtil.show(context, "点击Item" + position);
//                DiscoverRecyclerItemViewModel itemModel = (DiscoverRecyclerItemViewModel) viewModels.get(position);
//                itemModel.setListTitle("点击Item" + position);
////                progressDialog.setText("更新数据中...");
////                progressDialog.setMaxProgress(1000);
////                progressDialog.setProgress(800);
////                progressDialog.show();

//            }
//        });
    }
}
