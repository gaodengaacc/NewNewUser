package com.lyun.user.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.adapter.ServiceCardListAdapter;
import com.lyun.user.api.response.ServiceCardListItemResponse;
import com.lyun.user.eventbusmessage.EventIntentActivityMessage;
import com.lyun.user.eventbusmessage.EventListItemMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gordon
 * @since 2017/7/31
 * do()
 */

public class FragmentServiceCardViewModel extends ViewModel {
    public final ObservableField<BaseRecyclerAdapter> adapter = new ObservableField<>();
    public final ObservableField<List<ViewModel>> notifyData = new ObservableField<>();
    public final ObservableInt footerLayout = new ObservableInt();
    //设置LayoutManager
    public RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(AppApplication.getInstance());
    public static String TOP_CLICK_FLAG = "fragmentTop";

    public FragmentServiceCardViewModel() {
        init();
    }

    private void init() {

        List<ServiceCardItemViewModel> data = new ArrayList<>();
        data.add(new ServiceCardItemViewModel(new ServiceCardListItemResponse("律云法律服务", "999", 0)));
        data.add(new ServiceCardItemViewModel(new ServiceCardListItemResponse("律云法律服务", "6999", 1)));
        data.add(new ServiceCardItemViewModel(new ServiceCardListItemResponse("律云法律服务", "9999", 2)));
        ServiceCardListAdapter adapter = new ServiceCardListAdapter(data, R.layout.item_service_card_layout);
        adapter.setItemClickListener((view, viewModels, position) -> {
            EventListItemMessage message = new EventListItemMessage();
            message.setMessage(data.get(position).data);
            EventBus.getDefault().post(message);
        });
        this.adapter.set(adapter);
        footerLayout.set(R.layout.service_card_item_footer_layout);
    }

    public void onClickView(View view) {
        EventBus.getDefault().post(new EventIntentActivityMessage(new Intent().putExtra("flag", TOP_CLICK_FLAG)));
    }
}
