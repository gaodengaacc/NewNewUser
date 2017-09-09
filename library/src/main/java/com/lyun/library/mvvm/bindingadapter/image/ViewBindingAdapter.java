package com.lyun.library.mvvm.bindingadapter.image;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.utils.GlideUtils;

/**
 * Created by kelin on 16-3-24.
 */
public final class ViewBindingAdapter {

    @BindingAdapter({"uri"})
    public static void setImageUri(ImageView imageView, String uri) {
        if (!TextUtils.isEmpty(uri)) {
           GlideUtils.showImage(imageView.getContext(),imageView,uri);
        }
    }

    @BindingAdapter("imageSrc")
    public static void setImageSrc(ImageView imageView, int resid) {
        imageView.setImageResource(resid);
    }
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

    @BindingAdapter(value = {"uri", "placeholderImageRes", "onSuccessCommand", "onFailureCommand"}, requireAll = false)
    public static void loadImage(final ImageView imageView, String uri,
                                 @DrawableRes int placeholderImageRes,
                                 final RelayCommand<ImageView> onSuccessCommand,
                                 final RelayCommand<ImageView> onFailureCommand) {
        imageView.setImageResource(placeholderImageRes);
        if (!TextUtils.isEmpty(uri)) {
            Glide.with(imageView.getContext()).load(uri).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    if (onFailureCommand != null) {
                        onFailureCommand.execute(imageView);
                    }
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    if (onSuccessCommand != null) {
                        onSuccessCommand.execute(imageView);
                    }
                    return false;
                }
            }).into(imageView);
        }
    }
}

