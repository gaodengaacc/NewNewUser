package com.lyun.library.mvvm.bindingadapter.linearlayout;

import android.databinding.BindingAdapter;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

/**
 * Created by 郑成裕 on 2017/1/4.
 */

public class ViewBindingAdapter {

    @BindingAdapter("globalLayoutListener")
    public static void linearLayoutGetWidth(final LinearLayout layout, final GetWidthListener listener) {
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listener.getWidthListening(layout.getWidth());
            }
        });
    }

    public interface GetWidthListener {
        public void getWidthListening(int width);
    }

}
