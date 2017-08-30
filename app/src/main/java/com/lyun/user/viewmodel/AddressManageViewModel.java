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
import com.lyun.user.api.request.BaseRequestBean;
import com.lyun.user.api.request.DoAddressRequestBean;
import com.lyun.user.api.response.AddressItemResponse;
import com.lyun.user.eventbusmessage.EventIntentActivityMessage;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.model.AddressModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

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
    private AddressModel addressModel;

    public AddressManageViewModel() {
        init();
    }

    private void init() {
        addressModel = new AddressModel();
        queryAddress();
        listVisible.set(View.GONE);
        nullBgVisible.set(View.VISIBLE);
        itemData = new ArrayList<>();
        itemViewModelData = new ArrayList<>();
        itemAdapter = new AddressManageItemAdapter(itemViewModelData, R.layout.item_address_layout);
        this.adapter.set(itemAdapter);
    }

    public void queryAddress() {
        EventBus.getDefault().post(new EventProgressMessage(true));
        addressModel.queryAddress(new BaseRequestBean())
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(listAPIResult -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    return listAPIResult != null && listAPIResult.size() > 0;
                })
                .subscribe(listAPIResult -> {
                    itemData = listAPIResult;
                    setData();
                    listVisible.set(View.VISIBLE);
                    nullBgVisible.set(View.GONE);
                }, throwable -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    EventBus.getDefault().post(new EventToastMessage(throwable.getMessage()));
                });

    }

    public RelayCommand addAddressClick = new RelayCommand(() -> {
             EventBus.getDefault().post(new EventIntentActivityMessage(new Intent()));
    });

    public void setDefaultAddress(int position) {
        EventBus.getDefault().post(new EventProgressMessage(true));
        new AddressModel().defaultAddress(new DoAddressRequestBean(itemViewModelData.get(position).response.getId()))
                .flatMap(apiResult -> Observable.fromIterable(itemViewModelData))
                .map(responseViewModel -> {
                    if (responseViewModel.response.getIsDefault().equals("1"))
                        responseViewModel.setSelectBg(false);
                    return responseViewModel;
                })
                .filter(responseViewModel -> responseViewModel.position == itemViewModelData.size() - 1)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    itemViewModelData.get(position).setSelectBg(true);
                    notifyData.set(itemViewModelData);
                }, throwable -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    EventBus.getDefault().post(new EventToastMessage(throwable.getMessage()));
                });


    }

    public void deleteAddress(int position, boolean isBack) {
        EventBus.getDefault().post(new EventProgressMessage(true));
        Observable.just(isBack)
                .flatMap(back -> {
                            if (back)
                                return Observable.just(new Object());
                            else
                                return new AddressModel().deleteAddress(new DoAddressRequestBean(itemViewModelData.get(position).response.getId()));
                        }
                )
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    EventBus.getDefault().post(new EventToastMessage("删除成功"));
                    itemData.remove(position);
                    setData();
                    if (itemViewModelData.size() <= 0) {
                        listVisible.set(View.GONE);
                        nullBgVisible.set(View.VISIBLE);
                    }
                }, throwable -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    EventBus.getDefault().post(new EventToastMessage(throwable.getMessage()));
                });

    }

    public void updateAddress(int position,AddressItemResponse response) {
        queryAddress();
    }

    public void AddAddress(AddressItemResponse response) {
        queryAddress();
    }
    public void setData() {
        itemViewModelData.clear();
        for (AddressItemResponse response : itemData)
                    itemViewModelData.add(new AddressManageItemViewModel(response));
        itemAdapter = new AddressManageItemAdapter(itemViewModelData, R.layout.item_address_layout);
        adapter.set(itemAdapter);
    }

}
