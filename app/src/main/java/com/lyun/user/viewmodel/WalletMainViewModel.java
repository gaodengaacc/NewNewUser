package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.AppApplication;
import com.lyun.user.AppIntent;
import com.lyun.user.R;
import com.lyun.user.adapter.WalletMainRecorderAdapter;
import com.lyun.user.api.response.WalletChargeRecorderResponse;
import com.lyun.user.model.RemainingTimeModel;
import com.lyun.user.model.WalletChargeModel;
import com.lyun.utils.TimeUtil;
import com.lyun.widget.refresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Gordon
 * @since 2017/1/9
 * do()
 */

public class WalletMainViewModel extends ViewModel {
    public final ObservableField<BaseRecyclerAdapter> recorderAdapter = new ObservableField<>(); //记录adapter
    public final ObservableField<String> unUserTime = new ObservableField<>(); //剩余时间
    public final ObservableField<List<ViewModel>> notifyData = new ObservableField<>(); //记录条目更新
    public final ObservableInt refreshResult = new ObservableInt();//刷新结果
    public final ObservableInt loadMoreResult = new ObservableInt();//加载更多结果
    private List list;
    private int currentTranslationOrderPage = 0;//当前页码
    private int nextTranslationOrderPage = 0;//下一页码
    private int totalTranslationOrderPage = 0;//总页码
    private long unTime;
    private Intent intent;
    private Bundle bundle = new Bundle();

    public WalletMainViewModel(GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel) {
        toolbarViewModel.title.set("钱包");
        toolbarViewModel.onBackClick.set((v) -> getActivity().finish());
        toolbarViewModel.functionImage.set(R.mipmap.wallet_main_function_des_icon);
        toolbarViewModel.functionLeftImage.set(R.mipmap.wallet_main_function_charge_icon);
        toolbarViewModel.onFunctionClick.set((v) -> {
            intent = new Intent(AppIntent.ACTION_AGREEMENT);
            bundle.putString("agreementType", "charge");
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        });
        toolbarViewModel.onFunctionLeftClick.set((v) -> {
            intent = new Intent(AppIntent.ACTION_WALLET_CHARGE);
            intent.putExtra("unUseTime", unTime);
            getActivity().startActivity(intent);
        });
        init();
    }

    public RelayCommand onChargeButtonClick = new RelayCommand(() -> {
        intent = new Intent(AppIntent.ACTION_WALLET_CHARGE);
        intent.putExtra("unUseTime", unTime);
        getActivity().startActivity(intent);
    });

    @Override
    public void onResume() {
        super.onResume();
        getRemainingTime();//获取剩余时间
        getChargeRecorder(0, true);
    }

    //设置recycleView的layoutManage
    public RelayCommand<RecyclerView> recyclerViewLayoutManageCommand = new RelayCommand<RecyclerView>(recyclerView -> {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    });
    //PullToRefreshLayout 刷新监听
    public PullToRefreshLayout.OnRefreshListener onRefreshListener = new PullToRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            getChargeRecorder(0, true);
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            if (nextTranslationOrderPage <= totalTranslationOrderPage) {
                getChargeRecorder(nextTranslationOrderPage, false);
            } else {
                ObservableNotifier.alwaysNotify(loadMoreResult, PullToRefreshLayout.DONE);
            }
        }
    };

    /**
     * 获取充值记录
     *
     * @param page    页码
     * @param refresh 是否刷新
     */
    private void getChargeRecorder(int page, boolean refresh) {
        new WalletChargeModel().getWalletChargeRecorder(Account.preference().getPhone(), page)
                .subscribeOn(Schedulers.newThread())//子线程执行
                .observeOn(AndroidSchedulers.mainThread())//主线程处理结果
                .subscribe(apiResult -> {
                    if (apiResult.isSuccess()) {
                        if (refresh)
                            list.clear();
                        if (apiResult.getContent().getData() != null && apiResult.getContent().getData().size() == 0 && !refresh) {
                            ObservableNotifier.alwaysNotify(loadMoreResult, PullToRefreshLayout.DONE);
                            return;
                        }
                        for (WalletChargeRecorderResponse recorder : apiResult.getContent().getData()) {
                            WalletMainRecorderItemViewModel viewModel = new WalletMainRecorderItemViewModel();
                            viewModel.time.set(TimeUtil.formatTime(recorder.getAmountNowTime(), "yyyy-MM-dd HH:mm"));
                            viewModel.description.set("+" + TimeUtil.convertMin2Str(recorder.getAmountNow()));
                            list.add(viewModel);
                        }
                        ObservableNotifier.alwaysNotify(notifyData, list);
                        currentTranslationOrderPage = page;
                        nextTranslationOrderPage = currentTranslationOrderPage + 1;
                        totalTranslationOrderPage = apiResult.getContent().getPagecount();
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

    /**
     * 获取剩余时间·
     */
    private void getRemainingTime() {
        new RemainingTimeModel().getRemainingTime(Account.preference().getPhone())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    if (Integer.parseInt(apiResult.getContent().toString()) <= 0) {
                        unUserTime.set("0分钟");
                    } else {
                        unUserTime.set(TimeUtil.convertMin2Str(apiResult.getContent().toString()));
                    }
                    try {
                        unTime = Long.parseLong(apiResult.getContent().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    //初始化数据
    private void init() {
        list = new ArrayList<>();
        WalletMainRecorderAdapter adapter = new WalletMainRecorderAdapter(list, R.layout.wallet_main_recorder_item);
        recorderAdapter.set(adapter);
    }

    public RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(AppApplication.getInstance());
}
