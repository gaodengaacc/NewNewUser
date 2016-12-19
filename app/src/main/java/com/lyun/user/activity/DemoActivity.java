package com.lyun.user.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lyun.user.R;
import com.lyun.user.adapter.DemoRecycleAdapter;
import com.lyun.user.databinding.ActivityDemoBinding;
import com.lyun.user.model.DemoModel;
import com.lyun.user.viewmodel.DemoItemViewModel;
import com.lyun.user.viewmodel.DemoViewModel;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends GlobalTitleBarActivity {
    private DemoViewModel demoViewModel;
    private DemoRecycleAdapter adapter;
    private List<DemoItemViewModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDemoBinding activityDemoBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        demoViewModel = new DemoViewModel();
        demoViewModel.setAge(34);
        demoViewModel.setUsername("zhangsan");
        demoViewModel.setNickname("张三");
        demoViewModel.setUserIcon("http://img2.cache.netease.com/auto/2016/7/28/201607282215432cd8a.jpg");
        DemoModel demoModel = new DemoModel(this);
        demoModel.setDemoViewModel(demoViewModel);
        activityDemoBinding.setDemoViewModel(demoViewModel);
        activityDemoBinding.setDemoModel(demoModel);
        list = new ArrayList<DemoItemViewModel>();
        list.add(new DemoItemViewModel("1"));
        list.add(new DemoItemViewModel("2"));
        list.add(new DemoItemViewModel("3"));
        list.add(new DemoItemViewModel("4"));
        list.add(new DemoItemViewModel("5"));
        adapter = new DemoRecycleAdapter(this,list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activityDemoBinding.demoRecyclerView.setHasFixedSize(true);
        activityDemoBinding.demoRecyclerView.setLayoutManager(linearLayoutManager);
        activityDemoBinding.demoRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onBackClick(View view) {
        finish();
    }

    @Override
    protected void onTitleClick(View view) {

    }

    @Override
    protected void onFunctionClick(View view) {
        demoViewModel.setAge(100);
    }
}
