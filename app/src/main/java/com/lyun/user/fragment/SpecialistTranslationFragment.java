package com.lyun.user.fragment;

import android.databinding.Observable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.R;
import com.lyun.user.databinding.FragmentSpecialistTranslationBinding;
import com.lyun.user.dialog.LanguageChoicePopupWindow;
import com.lyun.user.viewmodel.SpecialistTranslationFragmentViewModel;

public class SpecialistTranslationFragment extends MvvmFragment<FragmentSpecialistTranslationBinding, SpecialistTranslationFragmentViewModel> {
    public SpecialistTranslationFragment() {
        // Required empty public constructor
    }
    public static SpecialistTranslationFragment newInstance() {
        SpecialistTranslationFragment fragment = new SpecialistTranslationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @NonNull
    @Override
    protected SpecialistTranslationFragmentViewModel createViewModel() {
        return new SpecialistTranslationFragmentViewModel(this.getContext());
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_specialist_translation;
    }


}
