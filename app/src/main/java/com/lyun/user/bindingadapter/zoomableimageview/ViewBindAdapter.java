package com.lyun.user.bindingadapter.zoomableimageview;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;

import com.netease.nim.uikit.common.ui.imageview.CropImageView;
import com.netease.nim.uikit.common.ui.imageview.MultiTouchZoomableImageView;


/**
 * @author Gordon
 * @since 2017/8/3
 * do()
 */

public class ViewBindAdapter {
    @BindingAdapter("output")
    public static void setOutPut(CropImageView view, OutPutInfo outPutInfo) {
        if (outPutInfo != null)
            view.setOutput(outPutInfo.x, outPutInfo.y);
    }

    @BindingAdapter("bitmap")
    public static void setBitmap(MultiTouchZoomableImageView view, Bitmap bitmap) {
        if (bitmap != null)
            view.setImageBitmap(bitmap);
    }

    public static class OutPutInfo {
        public int x;
        public int y;

        public OutPutInfo(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
