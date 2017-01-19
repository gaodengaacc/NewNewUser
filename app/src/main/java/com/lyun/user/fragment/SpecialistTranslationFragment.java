package com.lyun.user.fragment;

import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.R;
import com.lyun.user.databinding.FragmentSpecialistTranslationBinding;
import com.lyun.user.viewmodel.SpecialistTranslationFragmentViewModel;
import com.lyun.user.viewmodel.watchdog.ISpecialistTranslationFragmentViewModelCallbacks;

public class SpecialistTranslationFragment extends MvvmFragment<FragmentSpecialistTranslationBinding, SpecialistTranslationFragmentViewModel>
        implements ISpecialistTranslationFragmentViewModelCallbacks {
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
        return new SpecialistTranslationFragmentViewModel(this.getContext()).setPropertyChangeListener(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_specialist_translation;
    }


    @Override
    public void onRequestTranslation(BaseObservable observableField, int fieldId) {
    }
}
