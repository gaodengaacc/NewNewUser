package com.lyun.user.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.view.ViewPager;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.BR;
import com.lyun.user.R;

import net.funol.databinding.watchdog.annotations.WatchThis;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Created by ZHAOWEIWEI on 2017/7/31.
 */

public class LawWorldViewModel extends ViewModel {

    public final ObservableList<LawWorldCardViewModel> items = new ObservableArrayList<>();
    public final ObservableField<ItemView> itemView = new ObservableField<>();

    @WatchThis
    public final ObservableField<String> navigateDetail = new ObservableField<>();

    public LawWorldViewModel() {
        itemView.set(ItemView.of(BR.mvvm, R.layout.item_law_world));
        items.add(null);
        items.add(null);
    }

    public RelayCommand<ViewPager> onPagerClickListener = new RelayCommand<>(viewPager -> {
        // items.get(viewPager.getCurrentItem()).
        ObservableNotifier.alwaysNotify(navigateDetail, "");
    });

}
