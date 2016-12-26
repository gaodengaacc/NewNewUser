package com.lyun.viewmodel;

import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;

import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/21
 * do(RecycleView Item点击接口)
 */

public interface OnRecycleItemClickListener {
        void onItemClick(View view, List<ViewModel> viewModels, int position);
}
