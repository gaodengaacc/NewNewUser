package com.lyun.library.mvvm.bindingadapter.textview;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.widget.TextView;

public final class ViewBindingAdapter {

    @BindingAdapter(value = {"drawableLeft",
            "drawableTop",
            "drawableRight",
            "drawableBottom"}, requireAll = false)
    public static void setDrawables(TextView view,
                                       final int drawableLeft,
                                       final int drawableTop,
                                       final int drawableRight,
                                       final int drawableBottom) {
        if (view != null) {
            Resources res = view.getContext().getResources();
            view.setCompoundDrawablesWithIntrinsicBounds(drawableLeft != 0 ? res.getDrawable(drawableLeft) : null,
                    drawableTop != 0 ? res.getDrawable(drawableTop) : null,
                    drawableRight != 0 ? res.getDrawable(drawableRight) : null,
                    drawableBottom != 0 ? res.getDrawable(drawableBottom) : null);

        }
    }

    @BindingAdapter("backGroundRes")
    public static void setBackgroundResource(TextView textView, @DrawableRes int resid) {
        if (resid != 0)
            textView.setBackgroundResource(resid);
    }

    @BindingAdapter("typeFace")
    public static void setTypeface(TextView textView, Typeface typeface) {
        textView.setTypeface(typeface);
    }
}

