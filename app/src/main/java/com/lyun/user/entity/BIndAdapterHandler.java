package com.lyun.user.entity;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author Gordon
 * @since 2016/12/19
 * do()
 */

public class BIndAdapterHandler {
    //类比 activity_demo.xml 中的ImageView ：app:imageUrl（自定义标签加载imageUrl）
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).crossFade().into(imageView);

    }
}
