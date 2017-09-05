package com.lyun.user.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.MvvmActivity;
import com.lyun.user.R;
import com.lyun.user.api.response.LawWorldResponse;
import com.lyun.user.databinding.ActivityLawWorldDetailBinding;
import com.lyun.user.viewmodel.LawWorldDetailViewModel;


public class LawWorldDetailActivity extends MvvmActivity<ActivityLawWorldDetailBinding, LawWorldDetailViewModel> {

    public static final String EXTRA_DATA = "extra_data";

    public static void start(Context context, LawWorldResponse data) {
        Intent intent = new Intent(context, LawWorldDetailActivity.class);
        intent.putExtra(EXTRA_DATA, data);
        context.startActivity(intent);
    }

    @NonNull
    @Override
    protected LawWorldDetailViewModel createViewModel() {
        LawWorldResponse data = (LawWorldResponse) getIntent().getSerializableExtra(EXTRA_DATA);
        return new LawWorldDetailViewModel(data);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_law_world_detail;
    }
}
