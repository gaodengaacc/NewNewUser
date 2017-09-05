package com.lyun.user.fragment;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lyun.library.mvvm.view.fragment.MvvmFragment;
import com.lyun.user.R;
import com.lyun.user.activity.LawWorldDetailActivity;
import com.lyun.user.api.response.LawWorldResponse;
import com.lyun.user.databinding.FragmentLawWorldBinding;
import com.lyun.user.viewmodel.LawWorldViewModel;
import com.lyun.user.viewmodel.watchdog.ILawWorldViewModelCallbacks;
import com.lyun.utils.DisplayUtil;
import com.lyun.utils.L;

import net.funol.databinding.watchdog.Watchdog;

public class LawWorldFragment extends MvvmFragment<FragmentLawWorldBinding, LawWorldViewModel>
        implements ILawWorldViewModelCallbacks {

    private ViewPager mViewPager;

    public LawWorldFragment() {
    }

    public static LawWorldFragment newInstance() {
        LawWorldFragment fragment = new LawWorldFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @NonNull
    @Override
    protected LawWorldViewModel createViewModel() {

        mViewPager = (ViewPager) getFragmentViewDataBinding().getRoot().findViewById(R.id.law_world_viewpager);

        mViewPager.setPageMargin(DisplayUtil.dip2px(getContext(), 5));
        mViewPager.setPageTransformer(false, new LawWorldPageTransformer());
        mViewPager.setOffscreenPageLimit(4);

        // mViewPagerContainer = (RelativeLayout) root.findViewById(R.id.law_world_viewpager_container);
        // 引发bug
        // mViewPagerContainer.setOnTouchListener((v, event) -> mViewPager.dispatchTouchEvent(event));

        LawWorldViewModel viewModel = new LawWorldViewModel();
        Watchdog.newBuilder().watch(viewModel).notify(this).build();

        return viewModel;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_law_world;
    }

    @Override
    public void navigateDetail(ObservableField<LawWorldResponse> observableField, int fieldId) {
        LawWorldDetailActivity.start(getContext(), observableField.get());
    }

    public class LawWorldPageTransformer implements ViewPager.PageTransformer {

        private final float MAX_SCALE = 480f / 600;

        @Override
        public void transformPage(View page, float position) {
            float scale;
            if (position < -1) {
                // 左划到底
                scale = MAX_SCALE;
            } else if (position <= 0) {
                // 左划
                scale = 1 + position - position * MAX_SCALE;
            } else if (position <= 1) {
                // 右划
                scale = 1 - position + position * MAX_SCALE;
            } else {
                // 右划到底
                scale = MAX_SCALE;
            }

            L.e("PageTransformer", "position：" + position + "\tscale:" + scale);

            // 等比例缩放
            page.setScaleY(scale);
            page.setScaleX(scale);
            page.setTranslationY(page.getHeight() * (1 - scale));

            page.findViewById(R.id.law_world_card_mask).setAlpha(0.7f * (1 - scale) / (1 - MAX_SCALE));
        }
    }

}
