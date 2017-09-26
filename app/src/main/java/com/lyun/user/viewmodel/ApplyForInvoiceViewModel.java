package com.lyun.user.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.api.request.BaseRequestBean;
import com.lyun.user.api.response.AddressItemResponse;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.model.AddressModel;
import com.lyun.user.model.AfterSaleServiceModel;
import com.lyun.utils.FormatUtil;

import net.funol.databinding.watchdog.annotations.WatchThis;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApplyForInvoiceViewModel extends ViewModel {

    public final ObservableBoolean typePersonal = new ObservableBoolean(false);

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> invoiceCode = new ObservableField<>();
    public final ObservableField<String> address = new ObservableField<>();

    public AddressItemResponse mAddress;

    @WatchThis
    public final ObservableInt onChooseAddress = new ObservableInt();
    @WatchThis
    public final ObservableInt applyForInvoiceSuccess = new ObservableInt();

    private String orderNo;

    public ApplyForInvoiceViewModel(String orderNo) {
        this.orderNo = orderNo;
        initDefaultAddress();
    }

    public void setAddress(AddressItemResponse address) {
        mAddress = address;
        this.address.set(address.getRecipients() + "  " + FormatUtil.formatPhone(address.getPhoneNum()) + "\n"
                + address.getCity() + address.getDistrict() + address.getStreet() + address.getDetailAddress());
    }

    public RelayCommand chooseAddress = new RelayCommand(() -> ObservableNotifier.alwaysNotify(onChooseAddress, 0));

    public RelayCommand applyForInvoiceClick = new RelayCommand(() -> {
        if (!typePersonal.get()) {
            if (TextUtils.isEmpty(name.get())) {
                getToast().show("请填写单位名称");
                return;
            }
            if (TextUtils.isEmpty(invoiceCode.get())) {
                getToast().show("请填写纳税人识别号");
                return;
            }
        }
        if (mAddress == null) {
            getToast().show("请选择收件地址");
            return;
        }
        applyForInvoice();
    });

    public void applyForInvoice() {
        new AfterSaleServiceModel().applyForInvoice(orderNo, mAddress.getId(), typePersonal.get() ? "" : name.get(), typePersonal.get() ? "" : invoiceCode.get(), typePersonal.get() ? "0" : "1")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    if (result.isSuccess())
                        ObservableNotifier.alwaysNotify(applyForInvoiceSuccess, 0);
                    else
                        getToast().show(result.getDescribe());
                }, throwable -> getToast().show(throwable.getMessage()));
    }

    public void initDefaultAddress() {
        new AddressModel().queryAddress(new BaseRequestBean())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listAPIResult -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    if (listAPIResult != null && listAPIResult.size() > 0) {

                        boolean hasDefault = false;

                        for (AddressItemResponse address : listAPIResult) {
                            if (address != null && address.getIsDefault() == 1) {
                                setAddress(address);
                                hasDefault = true;
                            }
                        }
                        if(!hasDefault){
                            setAddress(listAPIResult.get(0));
                        }
                    }
                }, throwable -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    EventBus.getDefault().post(new EventToastMessage(throwable.getMessage()));
                });

    }

}
