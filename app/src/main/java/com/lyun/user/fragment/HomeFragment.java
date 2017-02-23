package com.lyun.user.fragment;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.R;
import com.lyun.user.databinding.FragmentHomeBinding;
import com.lyun.user.im.session.SessionHelper;
import com.lyun.user.model.TranslationOrderModel;
import com.lyun.user.viewmodel.HomeFragmentViewModel;
import com.lyun.user.viewmodel.watchdog.IHomeFragmentViewModelCallbacks;

public class HomeFragment extends MvvmFragment<FragmentHomeBinding, HomeFragmentViewModel>
        implements IHomeFragmentViewModelCallbacks {

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
        return new HomeFragmentViewModel().setPropertyChangeListener(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void onTranslationOrderGenerated(ObservableField<TranslationOrderModel.OrderType> observableField, int fieldId) {
        SessionHelper.startP2PSession(getActivity(), "123456");
    }

    @Override
    public void onTranslationOrderGenerateFail(ObservableField<String> observableField, int fieldId) {
        Toast.makeText(getContext(),observableField.get(),Toast.LENGTH_LONG).show();
    }
}
