package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.api.response.LawWorldResponse;

import net.funol.databinding.watchdog.annotations.WatchThis;

import java.util.List;

public class LawWorldDetailViewModel extends ViewModel {

    public final ObservableField<LawWorldResponse> data = new ObservableField<>();

    public final ObservableField<String> domin1 = new ObservableField<>();
    public final ObservableInt domin1Visibility = new ObservableInt(View.GONE);
    public final ObservableField<String> domin2 = new ObservableField<>();
    public final ObservableInt domin2Visibility = new ObservableInt(View.GONE);
    public final ObservableField<String> domin3 = new ObservableField<>();
    public final ObservableInt domin3Visibility = new ObservableInt(View.GONE);

    @WatchThis
    public final ObservableField onTitleBackClick = new ObservableField();

    public LawWorldDetailViewModel(LawWorldResponse data) {
        this.data.set(data);
        List<LawWorldResponse.Domin> domins = data.getDominList();
        if (domins != null) {
            if (domins.size() >= 1) {
                domin1.set(domins.get(0).getName());
                domin1Visibility.set(View.VISIBLE);
            }
            if (domins.size() >= 2) {
                domin2.set(domins.get(1).getName());
                domin2Visibility.set(View.VISIBLE);
            }
            if (domins.size() >= 3) {
                domin3.set(domins.get(2).getName());
                domin3Visibility.set(View.VISIBLE);
            }
        }
        this.data.notifyChange();
    }

    public RelayCommand onBackClick = new RelayCommand(() -> ObservableNotifier.alwaysNotify(onTitleBackClick, null));

}
