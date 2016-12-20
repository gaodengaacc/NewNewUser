package com.lyun.user.model;

import android.app.Activity;
import android.view.View;

import com.lyun.user.viewmodel.DemoViewModel;
import com.lyun.utils.ToastUtil;

/**
 * @author Gordon
 * @since 2016/12/16
 * do(Activity的业务处理)
 */

public class DemoModel {
    private Activity activity;

    public void setDemoViewModel(DemoViewModel demoViewModel) {
        this.demoViewModel = demoViewModel;
    }

    private DemoViewModel demoViewModel;

    public DemoModel(Activity activity) {
        this.activity = activity;
    }

    public void onUserIconClick(View v) {
        if(demoViewModel!=null)
            demoViewModel.setAge(10);
        ToastUtil.show(activity, "点击图片");
    }
}
