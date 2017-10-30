package com.lyun.library.mvvm.bindingadapter.viewgroup;

import android.databinding.BindingAdapter;
import android.view.ViewGroup;

public final class ViewBindingAdapter {

    @BindingAdapter("backGround")
    public static void setBackGround(ViewGroup viewGroup, int res) {
        viewGroup.setBackgroundResource(res);
    }
}

