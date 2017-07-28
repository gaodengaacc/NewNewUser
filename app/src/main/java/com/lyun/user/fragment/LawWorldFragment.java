package com.lyun.user.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lyun.user.R;
import com.lyun.utils.DisplayUtil;

public class LawWorldFragment extends Fragment {

    private static final int[] drawableIds = new int[]{
            R.mipmap.guide_page1, R.mipmap.guide_page2,
            R.mipmap.guide_page3, R.mipmap.guide_page1,
            R.mipmap.guide_page2, R.mipmap.guide_page3};

    private ViewPager mViewPager;
    private RelativeLayout mViewPagerContainer;
    private MyPagerAdapter mPagerAdapter;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_law_world, container, false);
        mViewPager = (ViewPager) root.findViewById(R.id.law_world_viewpager);
        mPagerAdapter = new MyPagerAdapter(drawableIds, getContext());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setPageMargin(DisplayUtil.dip2px(getContext(), 5));
        mViewPager.setCurrentItem(1);
        mViewPager.setPageTransformer(true, new LawCardPageTransformer());

        mViewPagerContainer = (RelativeLayout) root.findViewById(R.id.law_world_viewpager_container);
        mViewPagerContainer.setOnTouchListener((v, event) -> mViewPager.dispatchTouchEvent(event));

        return root;
    }

    public class LawCardPageTransformer implements ViewPager.PageTransformer {

        private String TAG = "LawCardPageTransformer";

        private final float MAX_SCALE = 480f / 600;

        @Override
        public void transformPage(View page, float position) {
            float scale = MAX_SCALE;
            if (position < -1 || position > 1) {
                // 左划到底 右划到底
            } else if (position <= 0) {
                // 左划
                scale = 1 + position - position * MAX_SCALE;
            } else if (position <= 1) {
                // 右划
                scale = 1 - position + position * MAX_SCALE;
            }
            // 等比例缩放
            page.setScaleY(scale);
            page.setScaleX(scale);
            page.setTranslationY(page.getHeight() * (1 - scale));

            page.findViewById(R.id.law_world_card_mask).setAlpha(0.7f * (1 - scale) / (1 - MAX_SCALE));
        }
    }

    public class MyPagerAdapter extends PagerAdapter {

        private int[] mBitmapIds;
        private Context mContext;

        public MyPagerAdapter(int[] data, Context context) {
            mBitmapIds = data;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mBitmapIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View root = LayoutInflater.from(mContext).inflate(R.layout.item_law_world, container, false);
            container.addView(root);
            return root;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
