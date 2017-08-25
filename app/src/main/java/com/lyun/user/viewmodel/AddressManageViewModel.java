package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.adapter.AddressManageItemAdapter;
import com.lyun.user.api.response.AddressItemResponse;
import com.lyun.user.eventbusmessage.EventIntentActivityMessage;
import com.lyun.user.eventbusmessage.EventProgressMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author Gordon
 * @since 2017/8/22
 * do()
 */

public class AddressManageViewModel extends ViewModel {
    public final ObservableField<BaseRecyclerAdapter> adapter = new ObservableField<>();
    public final ObservableField<List<AddressManageItemViewModel>> notifyData = new ObservableField<>();
    public final ObservableInt nullBgVisible = new ObservableInt();
    public final ObservableInt listVisible = new ObservableInt();
    public List<AddressItemResponse> itemData;
    public List<AddressManageItemViewModel> itemViewModelData;
    //设置LayoutManager
    public RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(AppApplication.getInstance());
    public AddressManageItemAdapter itemAdapter;

    public AddressManageViewModel() {
        init();
    }

    private void init() {
        listVisible.set(View.VISIBLE);
        nullBgVisible.set(View.GONE);
        itemData = new ArrayList<>();
        itemViewModelData = new ArrayList<>();
        itemData.add(new AddressItemResponse("姓名", "13000000000", "上海市静安区江场三路181号盈科律师大厦", true));
        itemData.add(new AddressItemResponse("姓名", "13000000000", "上海市静安区江场三路181号盈科律师大厦", false));
        itemData.add(new AddressItemResponse("姓名", "13000000000", "上海市静安区江场三路181号盈科律师大厦", false));
        itemData.add(new AddressItemResponse("姓名", "13000000000", "上海市静安区江场三路181号盈科律师大厦", false));
        itemData.add(new AddressItemResponse("姓名", "13000000000", "上海市静安区江场三路181号盈科律师大厦", false));
        setData();
        itemAdapter = new AddressManageItemAdapter(itemViewModelData, R.layout.item_address_layout);
        this.adapter.set(itemAdapter);
    }

    public RelayCommand addAddressClick = new RelayCommand(() -> {
             EventBus.getDefault().post(new EventIntentActivityMessage(new Intent()));
    });

    public void setDefaultAddress(int position) {
        EventBus.getDefault().post(new EventProgressMessage(true));
        Observable.fromIterable(itemViewModelData)
                .filter(response -> response.response.isDefaultAddress())
                .subscribe(response -> {
                    response.setSelectBg(false);
                    itemViewModelData.get(position).setSelectBg(true);
                });
        System.out.print(itemData.toString());
        itemAdapter.setListData(itemViewModelData);
        EventBus.getDefault().post(new EventProgressMessage(false));
    }

    public void deleteAddress(int position) {
        boolean isDefault = itemViewModelData.get(position).response.isDefaultAddress();
        itemViewModelData.remove(position);
        if (itemViewModelData.size() <= 0) {
            listVisible.set(View.GONE);
            nullBgVisible.set(View.VISIBLE);
        } else if (isDefault) {
            itemViewModelData.get(0).setSelectBg(true);
        }
       itemAdapter.setListData(itemViewModelData);
    }
    public void updateAddress(int position,AddressItemResponse response) {
        itemViewModelData.get(position).setResponse(response);
    }

    public void setData() {
        itemViewModelData.clear();
        Observable.fromIterable(itemData)
                .subscribe(response -> {
                    itemViewModelData.add(new AddressManageItemViewModel(response));
                });
    }
}
