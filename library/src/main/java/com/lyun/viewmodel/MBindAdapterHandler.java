package com.lyun.viewmodel;

import android.databinding.BindingAdapter;

import com.lyun.widget.DonutProgress;

/**
 * @author Gordon
 * @since 2016/12/22
 * do()
 */

public class MBindAdapterHandler {
    @BindingAdapter("donut_max")
    public static void setMaxProgress(DonutProgress donutProgress,int progress) {
        donutProgress.setMax(progress);
    }
    @BindingAdapter("donut_progress")
    public static void setProgress(DonutProgress donutProgress,int progress) {
        donutProgress.setProgress(progress);
    }
}
