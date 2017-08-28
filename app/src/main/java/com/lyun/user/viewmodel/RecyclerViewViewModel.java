package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.api.response.APIPageResult;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.widget.refresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecyclerViewViewModel extends ViewModel {

    public final ObservableField<BaseRecyclerAdapter> mRecyclerAdapter = new ObservableField<>(); //记录adapter
    public final ObservableField<List<ViewModel>> mRecyclerData = new ObservableField<>(); //记录条目更新

    public final ObservableInt refreshResult = new ObservableInt();//刷新结果
    public final ObservableInt loadMoreResult = new ObservableInt();//加载更多结果

    private List<ViewModel> mRecyclerViewModels;

    private int mCurrentPage = 0;//当前页码
    private int mNextPage = 0;//下一页码
    private int mTotalPages = 0;//总页码

    public ObservableField<RecyclerView.LayoutManager> mLayoutManager = new ObservableField<>();

    public RecyclerViewAdapter mDataAdapter;

    public RecyclerViewViewModel(RecyclerView.LayoutManager layoutManager, RecyclerViewAdapter adapter) {
        mLayoutManager.set(layoutManager);
        mDataAdapter = adapter;
        mRecyclerAdapter.set(adapter);
        mRecyclerViewModels = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAfterSaleServiceHistory(0, true);
    }

    //PullToRefreshLayout 刷新监听
    public PullToRefreshLayout.OnRefreshListener onRefreshListener = new PullToRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            getAfterSaleServiceHistory(0, true);
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            if (mNextPage <= mTotalPages) {
                getAfterSaleServiceHistory(mNextPage, false);
            } else {
                ObservableNotifier.alwaysNotify(loadMoreResult, PullToRefreshLayout.DONE);
            }
        }
    };

    private void getAfterSaleServiceHistory(int page, boolean refresh) {
        ((Observable<APIResult<APIPageResult<List>>>) mDataAdapter.getPage(page))
                .subscribeOn(Schedulers.newThread())//子线程执行
                .observeOn(AndroidSchedulers.mainThread())//主线程处理结果
                .subscribe(apiResult -> {
                    if (apiResult.isSuccess()) {
                        if (refresh)
                            mRecyclerViewModels.clear();
                        if (apiResult.getContent().getData() != null && apiResult.getContent().getData().size() == 0 && !refresh) {
                            ObservableNotifier.alwaysNotify(loadMoreResult, PullToRefreshLayout.DONE);
                            return;
                        }
                        for (Object data : apiResult.getContent().getData()) {
                            ViewModel viewModel = mDataAdapter.createViewModel(data);
                            mRecyclerViewModels.add(viewModel);
                        }
                        ObservableNotifier.alwaysNotify(mRecyclerData, mRecyclerViewModels);
                        mCurrentPage = page;
                        mNextPage = mCurrentPage + 1;
                        mTotalPages = apiResult.getContent().getPagecount();
                        if (refresh) {
                            ObservableNotifier.alwaysNotify(refreshResult, PullToRefreshLayout.SUCCEED);
                        } else {
                            ObservableNotifier.alwaysNotify(loadMoreResult, PullToRefreshLayout.SUCCEED);
                        }
                    } else {
                        if (refresh) {
                            ObservableNotifier.alwaysNotify(refreshResult, PullToRefreshLayout.FAIL);
                        } else {
                            ObservableNotifier.alwaysNotify(loadMoreResult, PullToRefreshLayout.FAIL);
                        }
                    }
                }, throwable -> throwable.printStackTrace());
    }

    public abstract static class RecyclerViewAdapter<VDB extends ViewDataBinding, VM extends ViewModel, D> extends BaseRecyclerAdapter<VDB, VM> {

        public RecyclerViewAdapter() {
            super();
            setLayoutId(getLayoutRes());
            setViewModels(new ArrayList<>());
        }

        @Override
        public void viewBind(VM viewModel, VDB viewDataBinding, int position) {
            viewDataBinding.setVariable(getVariableId(), viewModel);
        }

        @LayoutRes
        protected abstract int getLayoutRes();

        @IdRes
        protected abstract int getVariableId();

        protected abstract VM createViewModel(D data);

        protected abstract Observable<APIResult<APIPageResult<List<D>>>> getPage(int page);
    }
}
