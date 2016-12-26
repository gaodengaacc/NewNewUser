package com.lyun.library.mvvm;

import android.databinding.ViewDataBinding;

import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * @author Gordon
 * @since 2016/12/21
 * do(Item的数据判断接口)
 */

public interface InterfaceBindView {
    void viewBind(ViewModel viewModel, ViewDataBinding viewDataBinding, int position);
}
