package com.lyun.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

import com.lyun.library.mvvm.model.BaseModel;

/**
 * @author Gordon
 * @since 2016/12/21
 * do(基类viewmodel)
 */

public abstract class BaseViewModel extends BaseObservable{
    protected Context context;
    protected BaseModel model;
    public BaseViewModel(Context context){
        this.context = context;
    }
    private void setModel(BaseModel model){
        this.model = model;
    }
}
