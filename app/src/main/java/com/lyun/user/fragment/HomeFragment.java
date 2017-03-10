package com.lyun.user.fragment;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.R;
import com.lyun.user.activity.WaitingForTranslatorActivity;
import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.user.databinding.FragmentHomeBinding;
import com.lyun.user.dialog.LanguagePickerDialog;
import com.lyun.user.service.TranslationOrder;
import com.lyun.user.viewmodel.HomeFragmentViewModel;
import com.lyun.user.viewmodel.LanguagePickerDialogViewModel;
import com.lyun.user.viewmodel.watchdog.IHomeFragmentViewModelCallbacks;

public class HomeFragment extends MvvmFragment<FragmentHomeBinding, HomeFragmentViewModel>
        implements IHomeFragmentViewModelCallbacks {

    private HomeFragmentViewModel mHomeFragmentViewModel;

    private LanguagePickerDialog mLanguagePickerDialog;
    private LanguagePickerDialogViewModel mLanguagePickerDialogViewModel;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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

    @NonNull
    @Override
    protected HomeFragmentViewModel createViewModel() {
        mHomeFragmentViewModel = new HomeFragmentViewModel().setPropertyChangeListener(this);
        mLanguagePickerDialogViewModel = new LanguagePickerDialogViewModel();
        mLanguagePickerDialog = new LanguagePickerDialog(getActivity(), mLanguagePickerDialogViewModel);
        mLanguagePickerDialogViewModel.onLanguagePicked.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                mHomeFragmentViewModel.mCurrentLanguage.set(((ObservableField<FindLanguageResponse>) observable).get());
            }
        });
        mLanguagePickerDialogViewModel.onLanguagePicked.notifyChange();
        return mHomeFragmentViewModel;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onPickLanguage(BaseObservable observableField, int fieldId) {
        mLanguagePickerDialog.show();
    }

    @Override
    public void onTranslationOrderGenerated(ObservableField<TranslationOrder> observableField, int fieldId) {
        Intent intent = new Intent(getActivity(), WaitingForTranslatorActivity.class);
        intent.putExtra(TranslationOrder.ORDER_ID, observableField.get().getOrderId());
        intent.putExtra(TranslationOrder.ORDER_TYPE, observableField.get().getOrderType());
        intent.putExtra(TranslationOrder.TARGET_LANGUAGE, observableField.get().getTargetLanguage());
        startActivity(intent);
    }


    @Override
    public void onTranslationOrderGenerateFail(ObservableField<String> observableField, int fieldId) {
        Toast.makeText(getContext(), observableField.get(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void progressDialogShow(ObservableBoolean observableField, int fieldId) {
        if (observableField.get())
            dialogViewModel.show();
        else
            dialogViewModel.dismiss();
    }
}
