package com.lyun.user.im.session.fragment;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyun.user.R;
import com.lyun.user.databinding.FragmentTranslationAudioMessageBinding;
import com.lyun.user.viewmodel.TranslationAudioMessageViewModel;
import com.lyun.utils.GlideUtils;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.common.ui.imageview.CircleImageView;

/**
 * Created by ZHAOWEIWEI on 2017/3/3.
 */

public class TranslationAudioMessageFragment extends TFragment {

    private TranslationAudioMessageViewModel mViewModel;
    private View rootView;

    private String translatorName;
    private String targetLanguage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_translation_audio_message, container, false);
        ViewDataBinding viewDataBinding = DataBindingUtil.bind(rootView);
        FragmentTranslationAudioMessageBinding translationAudioMessageBinding = (FragmentTranslationAudioMessageBinding) viewDataBinding;
        mViewModel = new TranslationAudioMessageViewModel().setPropertyChangeListener(getActivity());
        translationAudioMessageBinding.setMvvm(mViewModel);
        mViewModel.targetLanguage.set(targetLanguage);
        mViewModel.translatorName.set(translatorName);
        return rootView;
    }

    public void onServiceTimeChanged(String time) {
        mViewModel.translationTime.set(time);
    }

    public void setTranslatorName(String name) {
        this.translatorName = name;
    }

    public void setTranslatorTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(CircleImageView imageView, String url) {
        Context context = imageView.getContext();
        GlideUtils.showImage(context, imageView, url);
    }
}
