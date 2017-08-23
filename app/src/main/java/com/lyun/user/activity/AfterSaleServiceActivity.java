package com.lyun.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.user.R;
import com.lyun.user.databinding.ActivityAfterSaleServiceBinding;
import com.lyun.user.viewmodel.AfterSaleServiceViewModel;


public class AfterSaleServiceActivity extends GeneralToolbarActivity<ActivityAfterSaleServiceBinding, AfterSaleServiceViewModel> {

    public static void start(Context context) {
        context.startActivity(new Intent(context, AfterSaleServiceActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_after_sale_service;
    }

    @NonNull
    @Override
    protected AfterSaleServiceViewModel createBodyViewModel() {
        return new AfterSaleServiceViewModel();
    }

}
