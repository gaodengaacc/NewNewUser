package com.lyun.library.mvvm;

import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;

import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/21
 * do(RecycleView Item点击接口)
 */

public interface OnRecycleItemClickListener<VM extends ViewModel> {
        void onItemClick(View view, List<VM> viewModels, int position);
}
