package com.lyun.user.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.OnRecycleItemClickListener;
import com.lyun.library.mvvm.bindingadapter.recyclerview.ViewBindingAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.ObservableActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.activity.ServiceCategoryActivity;
import com.lyun.user.adapter.DiscoverRecyclerAdapter;
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
    public final ObservableField<List<ViewModel>> notifyData = new ObservableField<>();
    public final ObservableField<BaseRecyclerAdapter> adapter = new ObservableField<>();
    public final ObservableField<RelayCommand> listenerObservableField = new ObservableField<>();
    private ProgressDialog progressDialog;
    private final int REQUEST_CODE = 1000;
    public DiscoverFragmentViewModel(Context context) {
        super(context);
    }


    public void initData() {
        List<DiscoverRecyclerItemViewModel> list = new ArrayList<DiscoverRecyclerItemViewModel>();
        DiscoverRecyclerAdapter discoverRecyclerViewAdapter = new DiscoverRecyclerAdapter(getContext(), list,R.layout.item_discover_recyclerview);
//        discoverRecyclerViewAdapter.setItemClickListener(new OnRecycleItemClickListener() {
//            @Override
//            public void onItemClick(View view, List<ViewModel> viewModels, int position) {
//                DiscoverRecyclerItemViewModel itemViewModel = (DiscoverRecyclerItemViewModel) viewModels.get(position);
//                getToast().show(itemViewModel.listTitle.get() + position);
//                ObservableActivity.Request request = new ObservableActivity.Request(REQUEST_CODE, new Intent(getContext(), ServiceCategoryActivity.class)
//                                                                                                    .putExtra("languageCategory", "普通服务"));
//                getActivity().startActivityForResult(request);
////                activityFinish.set(true);
//            }
//        });
        adapter.set(discoverRecyclerViewAdapter);
//        replyCommandObservableField.set(onItemClickCommand);
        listenerObservableField.set(onItemClickCommand);
    }

    public RelayCommand<ViewBindingAdapter.ClickListenerData> onItemClickCommand = new RelayCommand<ViewBindingAdapter.ClickListenerData>((data) -> {
        DiscoverRecyclerItemViewModel itemViewModel = (DiscoverRecyclerItemViewModel) data.list.get(data.position);
        getToast().show(itemViewModel.listTitle.get() + data.position);
        ObservableActivity.Request request = new ObservableActivity.Request(REQUEST_CODE, new Intent(getContext(), ServiceCategoryActivity.class)
                                                                                             .putExtra("languageCategory", "普通服务"));
        getActivity().startActivityForResult(request);
    });
//  public  OnRecycleItemClickListener listener = new OnRecycleItemClickListener() {
//    @Override
//    public void onItemClick(View view, List<ViewModel> viewModels, int position) {
//        DiscoverRecyclerItemViewModel itemViewModel = (DiscoverRecyclerItemViewModel) viewModels.get(position);
//        getToast().show(itemViewModel.listTitle.get() + position);
//        ObservableActivity.Request request = new ObservableActivity.Request(REQUEST_CODE, new Intent(getContext(), ServiceCategoryActivity.class)
//                                                                                             .putExtra("languageCategory", "普通服务"));
//        getActivity().startActivityForResult(request);
//    }
//};

    public double divideWidth(int screenWidth, int picWidth, int retainValue) {
        BigDecimal screenBD = new BigDecimal(Double.toString(screenWidth));
        BigDecimal picBD = new BigDecimal(Double.toString(picWidth));
        return screenBD.divide(picBD, retainValue, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    public void moreBtnCLick(View view) {
        List<ViewModel> list = new ArrayList<ViewModel>();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            getToast().show("返回成功");
        }
    }
}
