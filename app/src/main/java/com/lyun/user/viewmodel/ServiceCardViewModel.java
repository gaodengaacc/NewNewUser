package com.lyun.user.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import com.lyun.library.mvvm.viewmodel.ViewModel;

import java.util.List;

/**
 * Created by ZHAOWEIWEI on 2017/7/31.
 */

public class ServiceCardViewModel extends ViewModel {

    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableInt price = new ObservableInt();

    public final ObservableList<ServiceCardServiceItemViewModel> itemViewModels = new ObservableArrayList<>();

    public ServiceCardViewModel(String name, int price, String image, List<ServiceCardServiceItemViewModel> itemViewModels) {
        this.name.set(name);
        this.price.set(price);
        this.image.set(image);
        this.itemViewModels.addAll(itemViewModels);
    }

}
