package com.lyun.user.fragment;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.R;
import com.lyun.user.activity.LoginActivity;
import com.lyun.user.databinding.FragmentUserCenterBinding;
import com.lyun.user.viewmodel.UserCenterFragmentViewModel;
import com.lyun.user.viewmodel.watchdog.IUserCenterFragmentViewModelCallbacks;

public class UserCenterFragment extends MvvmFragment<FragmentUserCenterBinding, UserCenterFragmentViewModel>
        implements IUserCenterFragmentViewModelCallbacks {

    public UserCenterFragment() {
        // Required empty public constructor
    }

    public static UserCenterFragment newInstance() {
        UserCenterFragment fragment = new UserCenterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected UserCenterFragmentViewModel createViewModel() {
        return new UserCenterFragmentViewModel().setPropertyChangeListener(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_user_center;
    }

    @Override
    public void onLogout(BaseObservable observableField, int fieldId) {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}
