package com.lyun.user.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.lyun.library.mvvm.viewmodel.ViewModel;

import java.util.List;

import static com.lyun.user.Constants.API_BASE_URL;

/**
 * Created by ZHAOWEIWEI on 2017/7/31.
 */

public class ServiceCardViewModel extends ViewModel {

    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableDouble price = new ObservableDouble();

    public final ObservableList<ServiceCardServiceItemViewModel> itemViewModels = new ObservableArrayList<>();
    public String id;

    public ServiceCardViewModel(String name, double price, String image, String id, List<ServiceCardServiceItemViewModel> itemViewModels) {
        this.id = id;
        this.name.set(name);
        this.price.set(price);
        this.image.set(API_BASE_URL + "card-image?name=" + image);
        this.itemViewModels.addAll(itemViewModels);
    }

}
