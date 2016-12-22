package com.lyun.user.model;

import android.app.Activity;
import android.view.View;

import com.lyun.model.BaseModel;
import com.lyun.user.activity.DemoActivity;
import com.lyun.user.viewmodel.DemoViewModel;
import com.lyun.utils.ToastUtil;
import com.lyun.viewmodel.BaseViewModel;

/**
 * @author Gordon
 * @since 2016/12/16
 * do(Activity的业务处理)
 */

public class DemoModel extends BaseModel {
    private DemoActivity demoActivity;
    public DemoModel(Activity activity, BaseViewModel viewModel) {
        super(activity, viewModel);
        demoActivity = (DemoActivity)activity;
    }

    public void onUserIconClick(View v) {
        if(viewModel!=null)
            ((DemoViewModel)viewModel).setAge(10);
        ToastUtil.show(demoActivity, "点击图片");
    }

    @Override
    public void init() {
    }

    @Override
    public void doData() {
    }
}
