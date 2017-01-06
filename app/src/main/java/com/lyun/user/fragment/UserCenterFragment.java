package com.lyun.user.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.databinding.FragmentUserCenterBinding;
import com.lyun.user.viewmodel.MainActivityViewModel;
import com.lyun.user.viewmodel.UserCenterFragmentViewModel;

public class UserCenterFragment extends MvvmFragment<FragmentUserCenterBinding, UserCenterFragmentViewModel> {
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
        return new UserCenterFragmentViewModel();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_user_center;
    }

}
