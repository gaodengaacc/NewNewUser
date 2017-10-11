package com.lyun.library.mvvm.bindingadapter;

import android.databinding.BindingAdapter;

import com.netease.nim.uikit.common.ui.imageview.HeadImageView;

public class HeadImageViewBindingAdapter {

    @BindingAdapter("uid")
    public static void loadBuddyAvatar(HeadImageView imageView, String uid) {
        imageView.loadBuddyAvatar(uid);
    }

    @BindingAdapter("tid")
    public static void loadTeamIcon(HeadImageView imageView, String tid) {
        imageView.loadTeamIcon(tid);
    }

}
