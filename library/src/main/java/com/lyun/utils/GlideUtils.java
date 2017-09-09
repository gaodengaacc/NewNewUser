package com.lyun.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * @author Gordon
 * @since 2016/3/24
 * do(图片处理Util类)
 */
public class GlideUtils {
    /**
     * 加载图片
     *
     * @param view
     * @param resid
     * @param defaultResid
     */
    public static void showImage(Context context,ImageView view, int resid, int defaultResid) {
//        GlideApp.with(context)
//                .load(resid)
//                .placeholder(defaultResid)
//                .animate(R.anim.fade_in)
//                .crossFade()
//                .into(view);
        Glide.with(context)
                .load(resid)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(new RequestOptions().placeholder(defaultResid))
                .into(view);
    }
    /**
     * 加载网络图片
     *
     * @param view
     * @param url
     * @param defaultResid
     */
    public static void showImage(Context context,ImageView view, String url, int defaultResid) {
//        Glide.with(context)
//                .load(url)
//                .placeholder(defaultResid)
//                .animate(R.anim.fade_in)
//                .crossFade()
//                .into(view);
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(defaultResid))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(view);
    }
    /**
     * 加载本地图片
     *
     * @param view
     * @param file
     */
    public static void showImage(Context context,ImageView view, File file) {
//        Glide.with(context)
//                .load(file)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .animate(R.anim.fade_in)
//                .crossFade()
//                .into(view);
        Glide.with(context)
                .load(file)
                .apply(new RequestOptions().skipMemoryCache(false))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(view);
    }
    /**
     * 加载图片
     *
     * @param view
     * @param resid
     */
    public static void showImage(Context context,ImageView view, int resid) {
//        Glide.with(context)
//                .load(resid)
//                .animate(R.anim.fade_in)
//                .crossFade()
//                .into(view);
        Glide.with(context)
                .load(resid)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(view);
    }
    /**
     * 加载图片
     *
     * @param view
     * @param url
     */
    public static void showImage(Context context,ImageView view, String url) {
//        Glide.with(context)
//                .load(url)
//                .animate(R.anim.fade_in)
//                .crossFade()
//                .into(view);
        Glide.with(context)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(view);
    }

    /**
     * 加载图片
     *
     * @param view
     * @param url
     */
    public static void showImage(Context context, ImageView view, String url, boolean cache) {
//        Glide.with(context)
//                .load(url)
//                .skipMemoryCache(cache)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .animate(R.anim.fade_in)
//                .crossFade()
//                .into(view);
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .skipMemoryCache(cache)
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(view);
    }
    /**
     * 加载gif图片
     *
     * @param view
     * @param resid
     */
    public static void showGifImage(Context context,ImageView view, int resid) {
//        Glide.with(context)
//                .load(resid)
//                .asGif()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .animate(R.anim.fade_in)
//                .centerCrop()
//                .crossFade()
//                .into(view);
        Glide.with(context)
                .asGif()
                .load(resid)
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(view);
    }
    /**
     * 加载图片
     *
     * @param view
     * @param resid
     * @param defaultResid
     */
    public static void showMediumImage(ImageView view, String resid, int defaultResid) {

    }
    /**
     * 加载图片
     *
     * @param view
     * @param resid
     * @param defaultResid
     */
    public static void showBigImage(ImageView view, String resid, int defaultResid) {

    }


    /**
     * 加载图片
     *
     * @param view
     * @param resid
     * @param defaultResid
     */
    public static boolean showImageFromCache(ImageView view, String resid, int defaultResid) {
       return true;
    }


    /**
     * 加载图片
     *
     * @param view
     * @param resid
     * @param defaultResid
     */
    public static boolean showMediumImageFromCache(ImageView view, String resid, int defaultResid) {
        return true;
    }

    /**
     * 加载图片
     *
     * @param view
     * @param resid
     * @param defaultResid
     */
    public static boolean showBigImageFromCache(ImageView view, String resid, int defaultResid) {
        return true;
    }

}
