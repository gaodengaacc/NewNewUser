package com.lyun.user.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.R;
import com.lyun.user.databinding.FragmentRecyclerViewListBinding;
import com.lyun.user.viewmodel.RecyclerViewViewModel;

public class RecyclerViewFragment extends MvvmFragment<FragmentRecyclerViewListBinding, RecyclerViewViewModel> {

    private RecyclerViewViewModel mViewModel;

    public static RecyclerViewFragment newInstance(RecyclerViewViewModel.RecyclerViewAdapter adapter) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        fragment.setViewModel(new RecyclerViewViewModel(new LinearLayoutManager(fragment.getContext()), adapter));
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setViewModel(RecyclerViewViewModel viewModel) {
        this.mViewModel = viewModel;
    }

    @NonNull
    @Override
    protected RecyclerViewViewModel createViewModel() {
        mViewModel.setPropertyChangeListener(this);
        return mViewModel;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_recycler_view_list;
    }

}
