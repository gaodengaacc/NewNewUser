package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.AppApplication;
import com.lyun.user.R;
import com.lyun.user.adapter.UserServiceCardListAdapter;
import com.lyun.user.api.response.MyServiceCardResponse;
import com.lyun.user.eventbusmessage.EventListItemMessage;
import com.lyun.user.eventbusmessage.EventProgressMessage;
import com.lyun.user.eventbusmessage.EventToastMessage;
import com.lyun.user.model.ServiceCardModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Gordon
 * @since 2017/8/2
 * do()
 */

public class UserServiceCardListViewModel extends ViewModel {
    public final ObservableField<BaseRecyclerAdapter> adapter = new ObservableField<>();
    public final ObservableField<List<ServiceCardItemViewModel>> notifyData = new ObservableField<>();
    public final ObservableInt bgNullVisible = new ObservableInt();
    public final ObservableInt listVisible = new ObservableInt();
    //设置LayoutManager
    public RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(AppApplication.getInstance());
    public final ObservableInt footerLayout = new ObservableInt();
    private List<ServiceCardItemViewModel> data;
    private List<MyServiceCardResponse> responses;
    public UserServiceCardListViewModel(){
        init();
    }

    private void init() {
        bgNullVisible.set(View.GONE);
        queryMyCard();
        responses = new ArrayList<>();
        data = new ArrayList<>();
        UserServiceCardListAdapter adapter = new UserServiceCardListAdapter(data, R.layout.item_user_service_card_layout);
        adapter.setItemClickListener((view, viewModels, position) -> {
            EventBus.getDefault().post( new EventListItemMessage(data.get(position)));
        });
        this.adapter.set(adapter);
        footerLayout.set(R.layout.service_card_item_footer_layout);
    }

    private void queryMyCard() {
        EventBus.getDefault().post(new EventProgressMessage(true));
        new ServiceCardModel().queryMyServiceCardList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    if (result != null && result.size() > 0) {
                        bgNullVisible.set(View.GONE);
                        listVisible.set(View.VISIBLE);
                        responses = result;
                        setData();
                    } else {
                        bgNullVisible.set(View.VISIBLE);
                        listVisible.set(View.GONE);
                    }

                }, throwable -> {
                    EventBus.getDefault().post(new EventProgressMessage(false));
                    EventBus.getDefault().post(new EventToastMessage(throwable.getMessage()));
                });
    }

    public void setData() {
        data.clear();
        for (MyServiceCardResponse response : responses)
            data.add(new ServiceCardItemViewModel(response));
        notifyData.set(data);
    }
}

