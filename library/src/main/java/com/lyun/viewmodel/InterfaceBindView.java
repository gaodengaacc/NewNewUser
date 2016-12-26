package com.lyun.viewmodel;

import android.databinding.ViewDataBinding;

import com.lyun.library.mvvm.viewmodel.ViewModel;

/**
 * @author Gordon
 * @since 2016/12/21
 * do(Item的数据判断接口)
 */

public interface InterfaceBindView {
    void viewBind(ViewModel baseViewModel, ViewDataBinding viewDataBinding,int position);
}
