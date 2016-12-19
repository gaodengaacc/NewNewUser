package cn.com.law_cloud.user.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.com.law_cloud.user.R;
import cn.com.law_cloud.user.adapter.DemoRecycleAdapter;
import cn.com.law_cloud.user.databinding.ActivityDemoBinding;
import cn.com.law_cloud.user.model.DemoModel;
import cn.com.law_cloud.user.viewmodel.DemoItemViewModel;
import cn.com.law_cloud.user.viewmodel.DemoViewModel;

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
        list.add(new DemoItemViewModel("1"));
        list.add(new DemoItemViewModel("1"));
        list.add(new DemoItemViewModel("1"));
        list.add(new DemoItemViewModel("1"));
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
