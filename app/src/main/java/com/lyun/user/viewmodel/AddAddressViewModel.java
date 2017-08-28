package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.view.View;

import com.lyun.library.mvvm.bindingadapter.edittext.ViewBindingAdapter;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.api.response.AddressItemResponse;
import com.lyun.user.eventbusmessage.address.EventAddressSelectMessage;
import com.lyun.user.eventbusmessage.address.EventCityPickDialogShowMessage;

import org.greenrobot.eventbus.EventBus;

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
    public final ObservableInt clearVisible1 = new ObservableInt();
    public final ObservableInt clearVisible2 = new ObservableInt();
    public final ObservableInt clearVisible3 = new ObservableInt();
    private ObservableInt clearVisible;
    public int position;
    private   AddressItemResponse response;

    public AddAddressViewModel(Bundle bundle) {
        clearVisible1.set(View.GONE);
        clearVisible2.set(View.GONE);
        clearVisible3.set(View.GONE);
        if (bundle != null)
            init(bundle);
    }

    private void init(Bundle bundle) {
        response = (AddressItemResponse) bundle.get("response");
        position = bundle.getInt("position");
        username.set(response.getUserName());
        phoneNum.set(response.getPhoneNum());
        address.set(response.getAddress());
        moreAddress.set(response.getAddress());
    }

    public RelayCommand<ViewBindingAdapter.TextChangeData> editTextCommand = new RelayCommand<ViewBindingAdapter.TextChangeData>((data) -> {
        switch (data.viewId) {
            case R.id.address_username:
                clearVisible = clearVisible1;
                break;
            case R.id.address_phone:
                clearVisible = clearVisible2;
                break;
            case R.id.address_more:
                clearVisible = clearVisible3;
                break;
            default:
                break;
        }
        if (data.text.length() > 0)
            clearVisible.set(View.VISIBLE);
        else
            clearVisible.set(View.INVISIBLE);
    });

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
                response.setUserName(username.get());
                response.setPhoneNum(phoneNum.get());
                response.setAddress(address.get()+moreAddress.get());
                EventAddressSelectMessage message = new EventAddressSelectMessage(position,3);
                message.setResponse(response);
                EventBus.getDefault().post(message);
                break;
            default:
                break;
        }
    }
}
