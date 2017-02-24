package com.lyun.user.fragment;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.AppApplication;
import com.lyun.user.Constants;
import com.lyun.user.R;
import com.lyun.user.adapter.LanguageTextAdapter;
import com.lyun.user.api.response.FindLanguageResponse;
import com.lyun.user.databinding.FragmentHomeBinding;
import com.lyun.user.dialog.LanguagePickerDialog;
import com.lyun.user.im.session.SessionHelper;
import com.lyun.user.model.TranslationOrderModel;
import com.lyun.user.viewmodel.HomeFragmentViewModel;
import com.lyun.user.viewmodel.LanguagePickerDialogViewModel;
import com.lyun.user.viewmodel.watchdog.IHomeFragmentViewModelCallbacks;
import com.lyun.utils.ACache;

import java.util.List;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.WheelViewAdapter;

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

    @Override
    public void onResume() {
        super.onResume();
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
    public void onTranslationOrderGenerated(ObservableField<TranslationOrderModel.OrderType> observableField, int fieldId) {
        SessionHelper.startP2PSession(getActivity(), "13838502074");
    }


    @Override
    public void onTranslationOrderGenerateFail(ObservableField<String> observableField, int fieldId) {
        Toast.makeText(getContext(), observableField.get(), Toast.LENGTH_LONG).show();
    }

}
