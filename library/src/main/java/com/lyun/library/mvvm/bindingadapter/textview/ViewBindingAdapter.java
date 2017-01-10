package com.lyun.library.mvvm.bindingadapter.textview;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

public final class ViewBindingAdapter {
    @BindingAdapter("drawableLeft")
    public static void setDrawableLeft(TextView textView, int resId) {
        if(resId!=0)
        textView.setCompoundDrawablesWithIntrinsicBounds(textView.getResources().getDrawable(resId), null,null,null);
    }
}

