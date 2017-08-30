package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.lyun.library.mvvm.viewmodel.ViewModel;

public class ServiceCardServiceItemViewModel extends ViewModel {

    public final ObservableInt image = new ObservableInt();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> service = new ObservableField<>();

    public ServiceCardServiceItemViewModel(int image, String name, String service) {
        this.image.set(image);
        this.name.set(name);
        this.service.set(service);
    }

}
