package com.lyun.user.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.lyun.adapter.BaseRecyclerAdapter;
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

    //RecyclerView
    @BindingAdapter("notifyData")
    public static void setNotifyData(RecyclerView recyclerView, List<DiscoverRecyclerItemViewModel> data) {
        BaseRecyclerAdapter adapter = (BaseRecyclerAdapter) recyclerView.getAdapter();
        if(data!=null)
        adapter.setListData(data);
    }
    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView recyclerView,BaseRecyclerAdapter adapter){
        if(adapter!=null)
        recyclerView.setAdapter(adapter);
    }
    @BindingAdapter("isScroll")
    public static void setIsScroll(RecyclerView recyclerView, final boolean isScroll){
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 1) {
            @Override
            public boolean canScrollVertically() {
                return isScroll;
            }
        });//设置布局管理器,优化scrollview嵌套recyclerview惯性滑动
    }
}
