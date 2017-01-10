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

public class SpecialistTranslationFragment extends MvvmFragment<FragmentSpecialistTranslationBinding, SpecialistTranslationFragmentViewModel> implements View.OnClickListener {
    private SpecialistTranslationFragmentViewModel specialistTranslationFragmentViewModel;
    private FragmentSpecialistTranslationBinding fragmentSpecialistTranslationBinding;

    private int linearLayoutWidth;//控件宽度

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);
        specialistTranslationFragmentViewModel = getFragmentViewModel();
        fragmentSpecialistTranslationBinding = getFragmentViewDataBinding();

        addObservableViewModel(specialistTranslationFragmentViewModel);
        return view;
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    private void addObservableViewModel(final SpecialistTranslationFragmentViewModel viewModel) {
        viewModel.showPopupWindow.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                LanguageChoicePopupWindow languageChoicePopupWindow = new LanguageChoicePopupWindow(getActivity(), fragmentSpecialistTranslationBinding.linearLayoutLanguage.getWidth());//获取控件宽度
                backgroundAlpha(0.5f);
                languageChoicePopupWindow.showAsDropDown(fragmentSpecialistTranslationBinding.linearLayoutLanguage, 0, 0, Gravity.CENTER_HORIZONTAL);//设置显示在该控件的下方
                languageChoicePopupWindow.setChooseListener(viewModel.chooseListener);
                languageChoicePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1f);
                    }
                });
            }
        });
    }

    public void backgroundAlpha(float bgAlpha) {//设置背景明暗
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(layoutParams);
    }
}
