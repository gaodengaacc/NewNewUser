package com.lyun.user.im.session.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyun.user.R;
import com.lyun.user.databinding.FragmentTranslationAudioMessageBinding;
import com.lyun.user.viewmodel.TranslationAudioMessageViewModel;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nimlib.sdk.uinfo.constant.GenderEnum;

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
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.targetLanguage.set(targetLanguage);
        mViewModel.translatorName.set(translatorName);
    }

    public void onServiceTimeChanged(String time) {
        mViewModel.translationTime.set(time);
    }

    public void setTranslatorName(String name) {
        this.translatorName = name;
        if (mViewModel != null) {
            mViewModel.translatorName.set(name);
        }
    }

    public void setAvatar(String avatar) {
        if (mViewModel != null) {
            mViewModel.translatorAvatar.set(avatar);
        }
    }

    public void setAvatarPlaceHolder(@DrawableRes int avatarPlaceHolder) {
        if (mViewModel != null) {
            mViewModel.avatarPlaceHolder.set(avatarPlaceHolder);
        }
    }

    public void setTranslatorTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
        if (mViewModel != null) {
            mViewModel.targetLanguage.set(targetLanguage);
        }
    }
}
