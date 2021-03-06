package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.api.response.APIResult;
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
import io.reactivex.schedulers.Schedulers;

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
        nullBgVisible.set(View.GONE);
        itemData = new ArrayList<>();
        itemViewModelData = new ArrayList<>();
        itemAdapter = new AddressManageItemAdapter(itemViewModelData, R.layout.item_address_layout);
        this.adapter.set(itemAdapter);
    }

    public void queryAddress() {
        EventBus.getDefault().post(new EventProgressMessage(true));
        addressModel.queryAddress(new BaseRequestBean())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listAPIResult -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    if (listAPIResult != null && listAPIResult.size() > 0) {
                        itemData = listAPIResult;
                        setData();
                        listVisible.set(View.VISIBLE);
                        nullBgVisible.set(View.GONE);
                    } else {
                        listVisible.set(View.GONE);
                        nullBgVisible.set(View.VISIBLE);
                    }

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
        addressModel.defaultAddress(new DoAddressRequestBean(itemViewModelData.get(position).response.getId()))
                .flatMap(result -> Observable.create(observable -> {
                    if (!result.isSuccess()) {
                        observable.onError(new Throwable(result.getDescribe()));
                    } else {
                        observable.onNext(result);
                        observable.onComplete();
                    }
                }))
                .flatMap(apiResult -> Observable.fromIterable(itemViewModelData))
                .map(responseViewModel -> {
                    if (responseViewModel.response.getIsDefault() == 1)
                        responseViewModel.setSelectBg(false);
                    return responseViewModel;
                })
                .filter(responseViewModel -> responseViewModel.position == itemViewModelData.size() - 1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
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
                                return addressModel.deleteAddress(new DoAddressRequestBean(itemViewModelData.get(position).response.getId()));
                        }
                )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> Observable.create(observable -> {
                    if ((result instanceof APIResult) && !((APIResult) result).isSuccess()) {
                        observable.onError(new Throwable(((APIResult) result).getDescribe()));
                    } else {
                        observable.onNext(result);
                        observable.onComplete();
                    }
                }))
                .subscribe(response -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    EventBus.getDefault().post(new EventToastMessage("删除成功"));
                    itemData.remove(position);
                    itemViewModelData.remove(position);
                    itemAdapter.setListData(itemViewModelData);
                    if (itemViewModelData.size() <= 0) {
                        listVisible.set(View.GONE);
                        nullBgVisible.set(View.VISIBLE);
                    }
                }, throwable -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    EventBus.getDefault().post(new EventToastMessage(throwable.getMessage()));
                });

    }

    public void updateAddress(int position, AddressItemResponse response) {
        queryAddress();
    }

    public void AddAddress(AddressItemResponse response) {
        queryAddress();
    }

    public void setData() {
        itemViewModelData.clear();
        for (AddressItemResponse response : itemData)
            itemViewModelData.add(new AddressManageItemViewModel(response));
        itemAdapter.setListData(itemViewModelData);
    }

}
