package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.adapter.WalletMainRecorderAdapter;
import com.lyun.user.api.response.WalletChargeRecorderResponse;
import com.lyun.user.model.RemainingTimeModel;
import com.lyun.user.model.WalletChargeModel;
import com.lyun.widget.refresh.PullToRefreshLayout;

import net.funol.databinding.watchdog.annotations.WatchThis;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Gordon
 * @since 2017/1/9
 * do()
 */

public class WalletMainViewModel extends ViewModel {
    public final ObservableField<BaseRecyclerAdapter> recorderAdapter = new ObservableField<>();
    public final ObservableField<String> unUserTime = new ObservableField<>();
    public final ObservableField<List<ViewModel>> notifyData = new ObservableField<>();
    @WatchThis
    public final ObservableBoolean activityBg = new ObservableBoolean();
    public final ObservableInt refreshResult = new ObservableInt();
    public final ObservableInt loadMoreResult = new ObservableInt();
    private List list;
    private int currentTranslationOrderPage = 0;
    private int nextTranslationOrderPage = 0;
    private int totalTranslationOrderPage = 0;

    public WalletMainViewModel(GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel) {
        toolbarViewModel.title.set("钱包");
        toolbarViewModel.onBackClick.set((v) -> getActivity().finish());
        toolbarViewModel.functionImage.set(R.mipmap.wallet_main_function_des_icon);
        toolbarViewModel.functionLeftImage.set(R.mipmap.wallet_main_function_charge_icon);
        toolbarViewModel.onFunctionClick.set((v) -> showPop(v));
        toolbarViewModel.onFunctionLeftClick.set((v) -> {
            Intent intent = new Intent("com.lyun.user.intent.action.WALLET_CHARGE");
            getActivity().startActivity(intent);
        });
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        getRemainingTime();//获取剩余时间
        getChargeRecorder(0, true);
    }

    public RelayCommand<RecyclerView> recyclerViewLayoutManageCommand = new RelayCommand<RecyclerView>(recyclerView -> {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    });
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

    private void getChargeRecorder(int page, boolean refresh) {
        new WalletChargeModel().getWalletChargeRecorder(Account.preference().getPhone(), page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    if (apiResult.getStatus().equals("0")) {
                        if(refresh)
                            list.clear();
                        for (WalletChargeRecorderResponse recorder : apiResult.getContent().getData()) {
                            WalletMainRecorderItemViewModel viewModel = new WalletMainRecorderItemViewModel();
                            viewModel.time.set(recorder.getAmountNowTime());
                            viewModel.description.set("+" + recorder.getAmountNow() + "分钟");
                            list.add(viewModel);
                        }
                        notifyData.set(list);
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
                });
    }

    private void getRemainingTime() {
        new RemainingTimeModel().getRemainingTime(Account.preference().getPhone())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    unUserTime.set(apiResult.getContent().toString() + "分钟");
                });
    }

    private void init() {
        refreshResult.set(-1);
        loadMoreResult.set(-1);
        list = new ArrayList<>();
        WalletMainRecorderAdapter adapter = new WalletMainRecorderAdapter(list, R.layout.wallet_main_recorder_item);
        recorderAdapter.set(adapter);
    }

    public RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(AppApplication.getInstance());

    private void showPop(View v) {
        activityBg.set(true);
        WalletMainPopViewModel popWindow = new WalletMainPopViewModel(v.getContext());
        popWindow.setOnDismissListener(() -> activityBg.set(false));
        popWindow.showAsDropDown(v);
    }
}
