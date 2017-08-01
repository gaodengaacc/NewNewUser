package com.lyun.user.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityLawWorldDetailBinding;
import com.lyun.user.viewmodel.LawWorldDetailViewModel;


public class LawWorldDetailActivity extends MvvmActivity<ActivityLawWorldDetailBinding, LawWorldDetailViewModel> {

    public static void start(Context context) {
        context.startActivity(new Intent(context, LawWorldDetailActivity.class));
    }

    @NonNull
    @Override
    protected LawWorldDetailViewModel createViewModel() {
        return new LawWorldDetailViewModel();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_law_world_detail;
    }
}
