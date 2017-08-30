package com.lyun.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.R;
import com.lyun.user.activity.ServiceCardDetailActivity;
import com.lyun.user.api.response.ServiceCardListItemResponse;
import com.lyun.user.databinding.FragmentServiceCardBinding;
import com.lyun.user.eventbusmessage.EventListItemMessage;
import com.lyun.user.eventbusmessage.homefragment.EventMainIntentActivityMessage;
import com.lyun.user.viewmodel.FragmentServiceCardViewModel;
import com.lyun.utils.Screen;

import net.funol.databinding.watchdog.Watchdog;

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
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getFragmentViewDataBinding().layoutHeightTop.getLayoutParams();
        layoutParams.height = Screen.getStatusBarHeightByReflaction(getContext());
        getFragmentViewDataBinding().layoutHeightTop.setLayoutParams(layoutParams);

        ViewPager mViewPager = (ViewPager) getFragmentViewDataBinding().getRoot().findViewById(R.id.service_card_viewpager);

        //mViewPager.setPageMargin(DisplayUtil.dip2px(getContext(), -20));
        mViewPager.setPageTransformer(true, new ServiceCardPageTransformer());

        // mViewPagerContainer = (RelativeLayout) root.findViewById(R.id.law_world_viewpager_container);
        // 引发bug
        // mViewPagerContainer.setOnTouchListener((v, event) -> mViewPager.dispatchTouchEvent(event));

        FragmentServiceCardViewModel viewModel = new FragmentServiceCardViewModel();
        Watchdog.newBuilder().watch(viewModel).notify(this).build();

        return viewModel;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_service_card;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyStartActivity(EventMainIntentActivityMessage message) {
        //if (message.getMessage().getStringExtra("flag").equals(FragmentServiceCardViewModel.TOP_CLICK_FLAG))
        //    startActivity(new Intent(getContext(), ServiceCardTasteDetailActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemClick(EventListItemMessage message) {
        if (message.getMessage() instanceof ServiceCardListItemResponse) {
            ServiceCardListItemResponse response = (ServiceCardListItemResponse) message.getMessage();
            startActivity(new Intent(getContext(), ServiceCardDetailActivity.class).putExtra("cardId", response.getCardId()));
        }
    }

    public class ServiceCardPageTransformer implements ViewPager.PageTransformer {

        private final float MAX_SCALE = 270f / 360;
        private final float MAX_TRANSLATION_X_RATIO = 250f /330;

        @Override
        public void transformPage(View page, float position) {
            float scale = MAX_SCALE;
            float translationX = 0;
            if (position < -1 || position > 1) {
                // 左划到底 右划到底
            } else if (position <= 0) {
                // 左划
                scale = 1 + position - position * MAX_SCALE;
                translationX = page.getWidth() * (1 - scale) * MAX_TRANSLATION_X_RATIO;
            } else if (position <= 1) {
                // 右划
                scale = 1 - position + position * MAX_SCALE;
                translationX = page.getWidth() * (scale - 1) *MAX_TRANSLATION_X_RATIO;
            }
            // 等比例缩放
            page.setScaleY(scale);
            page.setScaleX(scale);
            page.setTranslationX(translationX);
        }
    }
}
