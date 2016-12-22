package com.lyun.model;

import android.app.Activity;
import android.content.Context;
import com.lyun.fragment.BaseFragment;
import com.lyun.viewmodel.BaseViewModel;

/**
 * @author Gordon
 * @since 2016/12/21
 * do()
 */

public abstract class BaseModel implements InterfaceModel {
    protected Activity activity;
    protected BaseFragment fragment;
    protected Context context;
    protected BaseViewModel viewModel;

    public BaseModel(Activity activity, BaseViewModel viewModel) {
        this.activity = activity;
        this.viewModel = viewModel;
    }

    ;

    public BaseModel(BaseFragment fragment, BaseViewModel viewModel) {
        this.fragment = fragment;
        this.viewModel = viewModel;
    }

    ;

    public BaseModel(Context context, BaseViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    ;

    public BaseModel(Context context) {
        this.context = context;
    }

    ;
}
