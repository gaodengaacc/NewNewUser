package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.adapter.UserServiceCardListAdapter;
import com.lyun.user.api.response.ServiceCardListItemResponse;
import com.lyun.user.eventbusmessage.EventListItemMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gordon
 * @since 2017/8/2
 * do()
 */

public class UserServiceCardListViewModel extends ViewModel {
    public final ObservableField<BaseRecyclerAdapter> adapter = new ObservableField<>();
    public final ObservableField<List<ViewModel>> notifyData = new ObservableField<>();
    //设置LayoutManager
    public RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(AppApplication.getInstance());
    public final ObservableInt footerLayout = new ObservableInt();
    public UserServiceCardListViewModel(){
        init();
    }

    private void init() {
        List<ServiceCardItemViewModel> data = new ArrayList<>();
        data.add(new ServiceCardItemViewModel(new ServiceCardListItemResponse("律云法律服务","999",0)));
        data.add(new ServiceCardItemViewModel(new ServiceCardListItemResponse("律云法律服务","6999",1)));
        data.add(new ServiceCardItemViewModel(new ServiceCardListItemResponse("律云法律服务","9999",2)));
        UserServiceCardListAdapter adapter = new UserServiceCardListAdapter(data, R.layout.item_user_service_card_layout);
        adapter.setItemClickListener((view, viewModels, position) -> {
            EventListItemMessage message = new EventListItemMessage();
            message.setMessage(data.get(position));
            EventBus.getDefault().post(message);
        });
        this.adapter.set(adapter);
        footerLayout.set(R.layout.service_card_item_footer_layout);
    }
}

