package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.BR;
import com.lyun.user.R;
import com.lyun.user.adapter.ServiceCardListAdapter;
import com.lyun.user.api.response.ServiceCardListItemResponse;
import com.lyun.user.eventbusmessage.EventIntentActivityMessage;
import com.lyun.user.eventbusmessage.EventListItemMessage;
import com.lyun.user.model.ServiceCardModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * @author Gordon
 * @since 2017/7/31
 * do()
 */

public class FragmentServiceCardViewModel extends ViewModel {

    public final ObservableInt topVisible = new ObservableInt();//android 5.0以上显示，否则不显示

    public final ObservableList<ServiceCardViewModel> serviceCardViewModels = new ObservableArrayList<>();
    public final ObservableField<ItemView> serviceCardItemView = new ObservableField<>();

    public final ObservableField<BaseRecyclerAdapter> adapter = new ObservableField<>();
    public final ObservableField<List<ViewModel>> notifyData = new ObservableField<>();
    public final ObservableInt footerLayout = new ObservableInt();
    //设置LayoutManager
    public RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(AppApplication.getInstance());

    public FragmentServiceCardViewModel() {
        init();
    }

    private void init() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            topVisible.set(View.VISIBLE);
        } else {
            topVisible.set(View.GONE);
        }

        serviceCardItemView.set(ItemView.of(BR.mvvm, R.layout.item_service_card));
        ServiceCardModel model = new ServiceCardModel();
        model.setImage(R.mipmap.ic_card_vip_big);
        serviceCardViewModels.add(new ServiceCardViewModel(model));
        serviceCardViewModels.add(new ServiceCardViewModel(model));

        List<ServiceCardItemViewModel> data = new ArrayList<>();
        data.add(new ServiceCardItemViewModel(new ServiceCardListItemResponse("律云法律服务", "999", 0)));
        data.add(new ServiceCardItemViewModel(new ServiceCardListItemResponse("律云法律服务", "6999", 1)));
        data.add(new ServiceCardItemViewModel(new ServiceCardListItemResponse("律云法律服务", "9999", 2)));
        ServiceCardListAdapter adapter = new ServiceCardListAdapter(data, R.layout.item_service_card_layout);
        adapter.setItemClickListener((view, viewModels, position) -> {
            EventBus.getDefault().post(new EventListItemMessage(data.get(position).data));
        });
        this.adapter.set(adapter);
        footerLayout.set(R.layout.service_card_item_footer_layout);
    }

}
