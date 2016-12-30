package com.lyun.user.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.user.viewmodel.DiscoverRecyclerItemViewModel;
import com.lyun.utils.GlideUtils;

import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/19
 * do(自定义XML标签)
 */

public class BindAdapterHandler {
    //类比 activity_demo.xml 中的ImageView ：app:imageUrl（自定义标签加载imageUrl）
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        GlideUtils.showImage(context,imageView,url);
    }
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, int resid) {
        Context context = imageView.getContext();
        GlideUtils.showImage(context,imageView,resid);
    }

}
