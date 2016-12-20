package com.lyun.user.model;

import android.app.Activity;
import android.view.View;

import com.lyun.fragment.BaseFragment;
import com.lyun.user.R;
import com.lyun.user.activity.MainActivity;
import com.lyun.user.fragment.DiscoverFragment;
import com.lyun.user.viewmodel.DiscoverRecyclerItemViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/20
 * do(处理DiscoverFragment逻辑类)
 */

public class DiscoverFragmentModel {
    private BaseFragment fragment;
    public DiscoverFragmentModel(BaseFragment fragment){
        this.fragment = fragment;
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
        ((DiscoverFragment)fragment).getDiscoverRecyclerViewAdapter().setListData(list);
    }

}
