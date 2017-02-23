package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.Account;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.adapter.WalletMainRecorderAdapter;
import com.lyun.user.model.RemainingTimeModel;

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
    private String userName = "";
    private Intent intent;
    @WatchThis
    public final ObservableBoolean activityBg = new ObservableBoolean();

    public WalletMainViewModel(GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel) {
        toolbarViewModel.title.set("钱包");
        toolbarViewModel.onBackClick.set((v) -> getActivity().finish());
        toolbarViewModel.functionImage.set(R.mipmap.wallet_main_function_des_icon);
        toolbarViewModel.functionLeftImage.set(R.mipmap.wallet_main_function_charge_icon);
        toolbarViewModel.onFunctionClick.set((v) -> showPop(v));
        toolbarViewModel.onFunctionLeftClick.set((v) -> {
            intent = new Intent("com.lyun.user.intent.action.WALLET_CHARGE");
            Bundle bundle = new Bundle();
            bundle.putString("remainingTime", unUserTime.get());
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        });
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        getRemainingTime(Account.preference().getPhone());//获取剩余时间
    }

    private void getRemainingTime(String userName) {
        new RemainingTimeModel().getRemainingTime(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResult -> {
                    unUserTime.set(apiResult.getContent().toString());
                });
    }

    private void init() {
        List list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            WalletMainRecorderItemViewModel viewModel = new WalletMainRecorderItemViewModel();
            viewModel.time.set("2016-11-02");
            viewModel.description.set("-100元");
            list.add(viewModel);
        }
        WalletMainRecorderAdapter adapter = new WalletMainRecorderAdapter(list, R.layout.wallet_main_recorder_item);
        recorderAdapter.set(adapter);
//        notifyData.set(list);
    }

    public RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(AppApplication.getInstance());

    private void showPop(View v) {
        activityBg.set(true);
        WalletMainPopViewModel popWindow = new WalletMainPopViewModel(v.getContext());
        popWindow.setOnDismissListener(() -> activityBg.set(false));
        popWindow.showAsDropDown(v);
    }
}
