package com.lyun.user.model;

import android.view.View;

import com.lyun.fragment.BaseFragment;
import com.lyun.model.BaseModel;
import com.lyun.user.R;
import com.lyun.user.fragment.DiscoverFragment;
import com.lyun.user.viewmodel.DiscoverRecyclerItemViewModel;
import com.lyun.utils.ToastUtil;
import com.lyun.viewmodel.BaseViewModel;
import com.lyun.viewmodel.OnRecycleItemClickListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/20
 * do(处理DiscoverFragment逻辑类)
 */

public class DiscoverFragmentModel extends BaseModel {
    private DiscoverFragment discoverFragment;

    public DiscoverFragmentModel(BaseFragment fragment, BaseViewModel viewModel) {
        super(fragment, viewModel);
        discoverFragment = (DiscoverFragment) fragment;
    }

    public List<DiscoverRecyclerItemViewModel> initData(){
        List<DiscoverRecyclerItemViewModel> list= new ArrayList<DiscoverRecyclerItemViewModel>();
       int[] imagesId = new int[]{R.mipmap.image1, R.mipmap.image2, R.mipmap.image3};
        for(int i=0;i<3;i++){
            DiscoverRecyclerItemViewModel viewModel = new DiscoverRecyclerItemViewModel("一篇关于英国的指南","在英国可以感受西方文化,她,迷人又多面!", imagesId[i]);
            list.add(viewModel);
        }
      return list;
    }
    public double divideWidth(int screenWidth, int picWidth, int retainValue) {
        BigDecimal screenBD = new BigDecimal(Double.toString(screenWidth));
        BigDecimal picBD = new BigDecimal(Double.toString(picWidth));
        return screenBD.divide(picBD, retainValue, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
    public void moreBtnCLick(View view){
        List<DiscoverRecyclerItemViewModel> list= new ArrayList<DiscoverRecyclerItemViewModel>();
        int[] imagesId = new int[]{R.mipmap.image1, R.mipmap.image2, R.mipmap.image3};
        for(int i=0;i<3;i++){
            DiscoverRecyclerItemViewModel viewModel = new DiscoverRecyclerItemViewModel("新数据","在英国可以感受西方文化,她,迷人又多面!", imagesId[i]);
            list.add(viewModel);
        }
        discoverFragment.getDiscoverRecyclerViewAdapter().setListData(list);
    }

    @Override
    public void init() {
        discoverFragment.getDiscoverRecyclerViewAdapter().setItemClickListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(View view, List<BaseViewModel> viewModels, int position) {
                ToastUtil.show(context,"点击Item"+position);
                DiscoverRecyclerItemViewModel itemModel = (DiscoverRecyclerItemViewModel) viewModels.get(position);
                itemModel.setListTitle("点击Item"+position);
            }
        });
    }

    @Override
    public void doData() {

    }
}
