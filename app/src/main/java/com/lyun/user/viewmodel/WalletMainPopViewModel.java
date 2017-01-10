package com.lyun.user.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.adapter.WalletMainPopAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gordon
 * @since 2017/1/10
 * do()
 */

public class WalletMainPopViewModel extends ViewModel {
    public final ObservableField<BaseRecyclerAdapter> adapter = new ObservableField<>();
    public final ObservableField<List<ViewModel>> notifyData = new ObservableField<>();

    //设置LayoutManager
    public RelayCommand<RecyclerView> recyclerViewRelayCommand = new RelayCommand<RecyclerView>(recyclerView -> {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    });

    public WalletMainPopViewModel(Context context) {
        super(context);
        init();
    }

    private void init() {
        List<WalletMainPopDesItemViewModel> list = new ArrayList<>();
        list.add(new WalletMainPopDesItemViewModel("购买说明"));
        for (int i = 1; i < 4; i++) {
            list.add(new WalletMainPopDesItemViewModel(i + ",****************"));
        }
        list.add(new WalletMainPopDesItemViewModel("费率说明"));
        for (int i = 1; i < 5; i++) {
            list.add(new WalletMainPopDesItemViewModel(i + ",****************"));
        }
        WalletMainPopAdapter popAdapter = new WalletMainPopAdapter(getContext(), list, R.layout.item_wallet_main_popwindow);
        adapter.set(popAdapter);
    }
}
