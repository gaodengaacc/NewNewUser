package com.lyun.library.mvvm.bindingadapter.wheelview;

import android.databinding.BindingAdapter;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.WheelViewAdapter;

/**
 * Created by 郑成裕 on 2017/1/16.
 */

public class ViewBindingAdapter {
    @BindingAdapter("ChangingListener")
    public static void addChangingListener(final WheelView wheelView, OnWheelChangedListener changedlistener) {
        wheelView.addChangingListener(changedlistener);
    }

    @BindingAdapter("ScrollingListener")
    public static void addScrollingListener(final WheelView wheelView, OnWheelScrollListener scrollListener) {
        wheelView.addScrollingListener(scrollListener);
    }

    @BindingAdapter("WheelViewAdapter")
    public static void setViewAdapter(final WheelView wheelView, WheelViewAdapter wheelViewAdapter) {
        if (wheelViewAdapter != null) {
            wheelView.setViewAdapter(wheelViewAdapter);
        }
    }

    @BindingAdapter("VisibleItems")
    public static void setVisibleItems(final WheelView wheelView, int count) {
        wheelView.setVisibleItems(count);
    }

    @BindingAdapter("CurrentItem")
    public static void setCurrentItem(final WheelView wheelView, int index) {
        wheelView.setCurrentItem(index);
    }

    @BindingAdapter("Cyclic")
    public static void setCyclic(final WheelView wheelView, boolean isCyclic) {
        wheelView.setCyclic(isCyclic);
    }

    @BindingAdapter("WheelForeground")
    public static void setWheelForeground(final WheelView wheelView, int resource) {
        wheelView.setWheelForeground(resource);
    }

}
