package com.lyun.library.mvvm.bindingadapter.image;

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
import com.bumptech.glide.request.RequestOptions;
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
            GlideUtils.showImage(imageView, uri);
        }
    }

    @BindingAdapter("imageSrc")
    public static void setImageSrc(ImageView imageView, int resid) {
        imageView.setImageResource(resid);
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        GlideUtils.showImage(imageView, url);
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, int resid) {
        GlideUtils.showImage(imageView, resid);
    }

    @BindingAdapter(value = {"uri", "placeholderImageRes", "onSuccessCommand", "onFailureCommand"}, requireAll = false)
    public static void loadImage(final ImageView imageView, String uri,
                                 @DrawableRes int placeholderImageRes,
                                 final RelayCommand<ImageView> onSuccessCommand,
                                 final RelayCommand<ImageView> onFailureCommand) {
        Glide.with(imageView)
                .load(uri)
                .apply(new RequestOptions().placeholder(placeholderImageRes))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        e.printStackTrace();
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

