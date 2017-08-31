package com.lyun.user.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.lyun.library.mvvm.observable.util.ObservableNotifier;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.BR;
import com.lyun.user.R;
import com.lyun.user.api.response.LawWorldCardResponse;
import com.lyun.user.model.LawWorldModel;

import net.funol.databinding.watchdog.annotations.WatchThis;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Created by ZHAOWEIWEI on 2017/7/31.
 */

public class LawWorldViewModel extends ViewModel implements LawWorldCardViewModel.OnClickListener {

    public final ObservableList<LawWorldCardViewModel> items = new ObservableArrayList<>();
    public final ObservableField<ItemView> itemView = new ObservableField<>();

    @WatchThis
    public final ObservableField<LawWorldCardResponse> navigateDetail = new ObservableField<>();

    public LawWorldViewModel() {
        itemView.set(ItemView.of(BR.mvvm, R.layout.item_law_world));
        items.add(new LawWorldCardViewModel(this, null));
        items.add(new LawWorldCardViewModel(this, null));
        new LawWorldModel().queryLawyerList(0).subscribe();
    }

    public void onItemClick(LawWorldViewModel viewModel) {
    }

    @Override
    public void onClick(LawWorldCardResponse card) {
        ObservableNotifier.alwaysNotify(navigateDetail, card);
    }
}
