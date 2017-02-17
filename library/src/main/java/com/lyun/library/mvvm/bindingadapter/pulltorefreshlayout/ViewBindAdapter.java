package com.lyun.library.mvvm.bindingadapter.pulltorefreshlayout;

import android.databinding.BindingAdapter;

import com.lyun.widget.refresh.PullToRefreshLayout;

/**
 * @author Gordon
 * @since 2017/1/22
 * do()
 */

public class ViewBindAdapter {
    @BindingAdapter("refreshListener")
    public static void onRefreshListener(PullToRefreshLayout layout, PullToRefreshLayout.OnRefreshListener listener){
        layout.setOnRefreshListener(listener);
    }
    @BindingAdapter("autoRefresh")
    public static void setAutoRefresh(PullToRefreshLayout layout,boolean isAuto){
        if(isAuto)
        layout.autoRefresh();
    }
}
