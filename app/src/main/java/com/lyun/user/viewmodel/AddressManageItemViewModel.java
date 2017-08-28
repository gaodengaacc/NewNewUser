package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.api.response.AddressItemResponse;
import com.lyun.user.eventbusmessage.address.EventAddressSelectMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Gordon
 * @since 2017/8/22
 * do()
 */

public class AddressManageItemViewModel extends ViewModel {
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> phoneNum = new ObservableField<>();
    public final ObservableField<String> userAddress = new ObservableField<>();
    public final ObservableInt selectBg = new ObservableInt();
    public AddressItemResponse response;
    public int position;

    public AddressManageItemViewModel(AddressItemResponse response) {
        this.response = response;
        update();
    }

    public void setSelectBg(boolean isDefault) {
        response.setDefaultAddress(isDefault);
        if (response.isDefaultAddress()) selectBg.set(R.mipmap.icon_address_select);
        else selectBg.set(R.mipmap.icon_address_unselect);
    }

    public void setResponse(AddressItemResponse response) {
        this.response = response;
        update();
    }

    private void update() {
        if (response.isDefaultAddress()) selectBg.set(R.mipmap.icon_address_select);
        else selectBg.set(R.mipmap.icon_address_unselect);
        userName.set(response.getUserName());
        phoneNum.set(response.getPhoneNum());
        userAddress.set(response.getAddress());
    }

    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.address_select:
                if (response.isDefaultAddress()) return;
                EventBus.getDefault().post(new EventAddressSelectMessage(position, 0));
                break;
            case R.id.address_editor:
                EventBus.getDefault().post(new EventAddressSelectMessage(position, 1));
                break;
            case R.id.address_delete:
                EventBus.getDefault().post(new EventAddressSelectMessage(position, 2));
                break;
            default:
                break;
        }
    }

    public void init(int position) {
        this.position = position;
    }
}
