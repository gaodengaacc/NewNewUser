package com.lyun.user.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.R;
import com.lyun.user.databinding.FragmentRecyclerViewListBinding;
import com.lyun.user.viewmodel.RecyclerViewViewModel;

public class RecyclerViewFragment extends MvvmFragment<FragmentRecyclerViewListBinding, RecyclerViewViewModel> {

    private RecyclerViewViewModel.RecyclerViewAdapter mAdapter;

    public static RecyclerViewFragment newInstance(RecyclerViewViewModel.RecyclerViewAdapter adapter) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        fragment.setRecyclerViewAdapter(adapter);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setRecyclerViewAdapter(RecyclerViewViewModel.RecyclerViewAdapter adapter) {
        this.mAdapter = adapter;
    }

    @NonNull
    @Override
    protected RecyclerViewViewModel createViewModel() {
        return new RecyclerViewViewModel(new LinearLayoutManager(getActivity()), mAdapter).setPropertyChangeListener(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_recycler_view_list;
    }

}
