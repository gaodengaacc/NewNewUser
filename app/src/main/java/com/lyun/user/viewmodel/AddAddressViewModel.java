package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lyun.library.mvvm.bindingadapter.edittext.ViewBindingAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.api.request.AddAddressRequestBean;
import com.lyun.user.api.request.DoAddressRequestBean;
import com.lyun.user.api.response.AddressItemResponse;
import com.lyun.user.eventbusmessage.EventActivityFinishMessage;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.eventbusmessage.address.EventAddressSelectMessage;
import com.lyun.user.eventbusmessage.address.EventCityPickDialogShowMessage;
import com.lyun.user.model.AddressModel;
import com.lyun.utils.RegExMatcherUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;

/**
 * @author Gordon
 * @since 2017/8/22
 * do()
 */

public class AddAddressViewModel extends ViewModel {
    public final ObservableField<String> username = new ObservableField<>();
    public final ObservableField<String> phoneNum = new ObservableField<>();
    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableField<String> moreAddress = new ObservableField<>();
    public final ObservableInt userSelection = new ObservableInt();
    public final ObservableInt moreSelection = new ObservableInt();
    public final ObservableInt clearVisible1 = new ObservableInt();
    public final ObservableInt clearVisible2 = new ObservableInt();
    public final ObservableInt clearVisible3 = new ObservableInt();
    private ObservableInt clearVisible;
    public int position;
    private AddressItemResponse response;
    private String province;
    private String city;
    private String district;
    private String street;
    private boolean isEditor;
    private AddAddressRequestBean bean;

    public AddAddressViewModel(Bundle bundle) {
        clearVisible1.set(View.GONE);
        clearVisible2.set(View.GONE);
        clearVisible3.set(View.GONE);
        if (bundle != null) {
            isEditor = true;
            init(bundle);
        } else {
            response = new AddressItemResponse();
            isEditor = false;
        }

    }

    public void setAddress(String province, String city, String district, String street) {
        this.province = province;
        this.city = city;
        this.district = district;
        this.street = street;
        address.set(province + city + district + street);
    }
    private void init(Bundle bundle) {
        response = (AddressItemResponse) bundle.get("response");
        position = bundle.getInt("position");
        province = response.getProvince();
        city = response.getCity();
        district = response.getDistrict();
        street = response.getStreet();
        username.set(response.getRecipients());
        phoneNum.set(response.getPhoneNum());
        address.set(response.getProvince() + response.getCity() + response.getDistrict() + response.getStreet());
        moreAddress.set(response.getDetailAddress());
    }

    public RelayCommand<ViewBindingAdapter.TextChangeData> editTextCommand = new RelayCommand<ViewBindingAdapter.TextChangeData>((data) -> {
        switch (data.viewId) {
            case R.id.address_username:
                clearVisible = clearVisible1;
                checkLength(data.text, 20, username, data.view, "收件人名字");
                break;
            case R.id.address_phone:
                clearVisible = clearVisible2;
                break;
            case R.id.address_more:
                clearVisible = clearVisible3;
                checkLength(data.text, 50, moreAddress, data.view, "详细地址");
                break;
            default:
                break;
        }
        if (data.text.length() > 0)
            clearVisible.set(View.VISIBLE);
        else
            clearVisible.set(View.INVISIBLE);
    });

    public void checkLength(String text, int length, ObservableField<String> view, EditText editText, String name) {
        if (text.length() > length) {
            EventBus.getDefault().post(new EventToastMessage(name + "不能超过" + length));
            editText.setText(text.substring(0, length));
            editText.setSelection(editText.getText().length());
        }
    }

    public void onClearClick(View view) {
        switch (view.getId()) {
            case R.id.clear_text1:
                username.set("");
                clearVisible1.set(View.GONE);
                break;
            case R.id.clear_text2:
                phoneNum.set("");
                clearVisible2.set(View.GONE);
                break;
            case R.id.clear_text3:
                moreAddress.set("");
                clearVisible3.set(View.GONE);
                break;
            case R.id.address_address:
                EventBus.getDefault().post(new EventCityPickDialogShowMessage(true));
                break;
            case R.id.address_save:
                check();
                break;
            default:
                break;
        }
    }

    private void check() {
        if (isFastDoubleClick()) return;
        if (("".equals(username.get())) || (null == username.get())) {
            EventBus.getDefault().post(new EventToastMessage("请输入收件人"));
        } else if (("".equals(phoneNum.get())) || (null == phoneNum.get())) {
            EventBus.getDefault().post(new EventToastMessage("请输入手机号"));
        } else if (("".equals(address.get())) || (null == address.get())) {
            EventBus.getDefault().post(new EventToastMessage("请输入地址"));
        } else if (("".equals(moreAddress.get())) || (null == moreAddress.get())) {
            EventBus.getDefault().post(new EventToastMessage("请输入地址"));
        } else if (!RegExMatcherUtils.isMobileNO(phoneNum.get())) {
            EventBus.getDefault().post(new EventToastMessage("请输入有效手机号"));
        } else {
            doAddress();
        }
    }

    public void doAddress() {
        EventBus.getDefault().post(new EventProgressMessage(true));
        doEdit();
        Observable.just(isEditor)
                .flatMap(isEditor -> {
                    if (isEditor)
                        return new AddressModel().updateAddress(bean);
                    else
                        return new AddressModel().addAddress(bean);
                })
                .subscribe(apiResult -> {
                            EventBus.getDefault().post(new EventProgressMessage(false));
                            EventAddressSelectMessage message = new EventAddressSelectMessage(position, isEditor ? 3 : 4);
                            response.setId(apiResult+"");
                            message.setResponse(response);
                            EventBus.getDefault().post(new EventToastMessage("保存成功"));
                            EventBus.getDefault().post(message);
                            EventBus.getDefault().post(new EventActivityFinishMessage(true));
                        }, throwable -> {
                            EventBus.getDefault().post(new EventProgressMessage(false));
                            EventBus.getDefault().post(new EventToastMessage(throwable.getMessage()));
                        }
                );

    }

    public void deleteAddress() {
        EventBus.getDefault().post(new EventProgressMessage(true));
        new AddressModel().deleteAddress(new DoAddressRequestBean(response.getId()))
                .subscribe(apiResult -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    EventAddressSelectMessage message = new EventAddressSelectMessage(position, 5);
                    message.setResponse(response);
                    EventBus.getDefault().post(message);
                    EventBus.getDefault().post(new EventActivityFinishMessage(true));
                }, throwable -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    EventBus.getDefault().post(new EventToastMessage(throwable.getMessage()));
                });
    }

    public void doEdit() {
        bean = new AddAddressRequestBean();
        response.setRecipients(username.get());
        response.setPhoneNum(phoneNum.get());
        response.setProvince(province);
        response.setCity(city);
        response.setDistrict(district);
        response.setStreet(street);
        response.setDetailAddress(moreAddress.get());
        bean.setRecipients(response.getRecipients());
        bean.setPhoneNum(response.getPhoneNum());
        bean.setProvince(response.getProvince());
        bean.setCity(response.getCity());
        bean.setDistrict(response.getDistrict());
        bean.setStreet(response.getStreet());
        bean.setDetailAddress(response.getDetailAddress());
        bean.setId(response.getId());
    }

    private long lastClickTime;

    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
