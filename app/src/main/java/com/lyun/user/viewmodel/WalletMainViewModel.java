package com.lyun.user.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.adapter.WalletMainRecorderAdapter;
import com.lyun.user.dialog.WalletMainPopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gordon
 * @since 2017/1/9
 * do()
 */

public class WalletMainViewModel extends ViewModel {
    public final ObservableField<BaseRecyclerAdapter> recorderAdapter = new ObservableField<>();
    public final ObservableField<String> unUserTime = new ObservableField<>();
    public final ObservableField<String> balance = new ObservableField<>();
    public final ObservableField<List<ViewModel>> notifyData = new ObservableField<>();
    public final ObservableField<Boolean> activityBg = new ObservableField<>();

    public WalletMainViewModel(Context context,GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel) {
        super(context);
        toolbarViewModel.title.set("钱包");
        toolbarViewModel.onBackClick.set(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        toolbarViewModel.functionImage.set(R.mipmap.wallet_main_function_des_icon);
        toolbarViewModel.functionLeftImage.set(R.mipmap.wallet_main_function_charge_icon);
        toolbarViewModel.onFunctionLeftClick.set(onFunctionLeftListener);
        toolbarViewModel.onFunctionClick.set(onFunctionListener);
        init();
    }

    private void init() {
        unUserTime.set("2小时28分钟");
        balance.set("100.00元");
        List list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            WalletMainRecorderItemViewModel viewModel = new WalletMainRecorderItemViewModel();
            viewModel.time.set("2016-11-02");
            viewModel.description.set("-100元");
            list.add(viewModel);
        }
        WalletMainRecorderAdapter adapter = new WalletMainRecorderAdapter(getContext(), list, R.layout.wallet_main_recorder_item);
        recorderAdapter.set(adapter);
//        notifyData.set(list);
    }

    public RelayCommand<RecyclerView> recyclerViewRelayCommand = new RelayCommand<RecyclerView>(recyclerView -> {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    });
    private View.OnClickListener onFunctionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//          getActivity().startActivity(new Intent("com.lyun.user.intent.action.WALLET_CHARGE"));
            showPop(v);
        }
    };
    private View.OnClickListener onFunctionLeftListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             getActivity().startActivity(new Intent("com.lyun.user.intent.action.WALLET_CHARGE"));
        }
    };

    private void showPop(View v) {
        activityBg.set(false);
        WalletMainPopWindow popWindow = new WalletMainPopWindow(v.getContext());
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                activityBg.set(true);
            }
        });
        popWindow.showAsDropDown(v);
    }
}
