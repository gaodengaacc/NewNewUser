package com.lyun.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityLawWorldDetailBinding;
import com.lyun.user.viewmodel.LawWorldDetailViewModel;


public class LawWorldDetailActivity extends MvvmActivity<ActivityLawWorldDetailBinding, LawWorldDetailViewModel> {

    public static void start(Context context) {
        context.startActivity(new Intent(context, LawWorldDetailActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_world_detail);
    }

    @NonNull
    @Override
    protected LawWorldDetailViewModel createViewModel() {
        return new LawWorldDetailViewModel();
    }

    @NonNull
    @Override
    protected ViewModel getBodyViewModel() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return 0;
    }
}
