package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.command.consumer.Consumer0;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.api.response.LawWorldCardResponse;
import com.lyun.user.model.ServiceCardModel;

/**
 * Created by ZHAOWEIWEI on 2017/7/31.
 */

public class ServiceCardViewModel extends ViewModel {

    public final ObservableField<ServiceCardModel> data = new ObservableField<>();

    public final ObservableInt image = new ObservableInt();

    public ServiceCardViewModel(ServiceCardModel data) {
        this.data.set(data);
        image.set(data.getImage());
    }

}
