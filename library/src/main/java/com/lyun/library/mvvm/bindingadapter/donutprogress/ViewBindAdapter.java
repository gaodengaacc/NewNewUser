package com.lyun.library.mvvm.bindingadapter.donutprogress;

import android.databinding.BindingAdapter;

import com.lyun.widget.DonutProgress;

/**
 * @author Gordon
 * @since 2016/12/27
 * do()
 */

public class ViewBindAdapter {
    @BindingAdapter("donut_max")
    public static void setMaxProgress(DonutProgress donutProgress,int progress){
        donutProgress.setMax(progress);
    }
    @BindingAdapter("donut_progress")
    public static void setProgress(DonutProgress donutProgress,int progress){
        donutProgress.setProgress(progress);
    }
}
