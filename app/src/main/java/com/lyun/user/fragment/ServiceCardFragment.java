package com.lyun.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;

import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.R;
import com.lyun.user.activity.ServiceCardDetailActivity;
import com.lyun.user.activity.ServiceCardTasteDetailActivity;
import com.lyun.user.api.response.ServiceCardListItemResponse;
import com.lyun.user.databinding.FragmentServiceCardBinding;
import com.lyun.user.eventbusmessage.EventListItemMessage;
import com.lyun.user.eventbusmessage.homefragment.EventMainIntentActivityMessage;
import com.lyun.user.viewmodel.FragmentServiceCardViewModel;
import com.lyun.utils.DisplayUtil;
import com.lyun.utils.Screen;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ServiceCardFragment extends MvvmFragment<FragmentServiceCardBinding, FragmentServiceCardViewModel> {

    public ServiceCardFragment() {
    }

    public static ServiceCardFragment newInstance() {
        ServiceCardFragment fragment = new ServiceCardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @NonNull
    @Override
    protected FragmentServiceCardViewModel createViewModel() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(getContext(), 50));
        layoutParams.setMargins(0, Screen.getStatusBarHeightByReflaction(getContext()), 0, 0);
        getFragmentViewDataBinding().fragmentCardTop.setLayoutParams(layoutParams);
        return new FragmentServiceCardViewModel();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_service_card;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyStartActivity(EventMainIntentActivityMessage message) {
        if (message.getMessage().getStringExtra("flag").equals(FragmentServiceCardViewModel.TOP_CLICK_FLAG))
            startActivity(new Intent(getContext(), ServiceCardTasteDetailActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemClick(EventListItemMessage message) {
        if (message.getMessage() instanceof ServiceCardListItemResponse) {
            ServiceCardListItemResponse response = (ServiceCardListItemResponse) message.getMessage();
            startActivity(new Intent(getContext(), ServiceCardDetailActivity.class).putExtra("cardId",response.getCardId()));
        }
    }
}
