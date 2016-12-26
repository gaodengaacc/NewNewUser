package com.lyun.user.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lyun.fragment.BaseFragment;
import com.lyun.user.R;
import com.lyun.user.adapter.DiscoverRecyclerAdapter;
import com.lyun.user.adapter.DiscoverViewPagerAdapter;
import com.lyun.user.databinding.FragmentDiscoverBinding;
import com.lyun.user.viewmodel.DiscoverRecyclerItemViewModel;
import com.lyun.user.model.DiscoverFragmentModel;
import com.lyun.viewmodel.BaseViewModel;

import java.util.List;

public class DiscoverFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    ViewPager mViewPager;
    LinearLayout dotLinearLayout;//圆点布局
    RecyclerView mRecyclerView;
    private DiscoverViewPagerAdapter discoverViewPagerAdapter;
    private int[] mImageRes = {R.mipmap.image1, R.mipmap.image2, R.mipmap.image3};//图片资源
    private ImageView[] mDots;//圆点图片
    private final long delay = 3 * 1000;//延时3秒后viewpager图片自动跳转
    private final int AUTO = 0;
    private int width;
    private int newWidth;
    private int padding;
    private ImageView[][] mImageViews;

    private DiscoverRecyclerAdapter discoverRecyclerViewAdapter;
    private DiscoverFragmentModel discoverFragmentModel; //业务处理类

    public DiscoverFragment() {
        // Required empty public constructor
    }

    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DiscoverRecyclerAdapter getDiscoverRecyclerViewAdapter() {
        return discoverRecyclerViewAdapter;
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
        discoverFragmentModel = new DiscoverFragmentModel(this, new BaseViewModel());
        FragmentDiscoverBinding discoverBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover, container, false);
        mRecyclerView = discoverBinding.recyclerViewDiscover;
        mViewPager = discoverBinding.viewPagerDiscover;
        dotLinearLayout = discoverBinding.dotLinearLayout;
        discoverBinding.setModel(discoverFragmentModel);
        View view = discoverBinding.getRoot();
        initViewPager(view);
        List<DiscoverRecyclerItemViewModel> listData = discoverFragmentModel.initData();
        discoverRecyclerViewAdapter = new DiscoverRecyclerAdapter(getActivity(), listData, R.layout.item_discover_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });//设置布局管理器,优化scrollview嵌套recyclerview惯性滑动
        mRecyclerView.setAdapter(discoverRecyclerViewAdapter);
        discoverFragmentModel.init();
        return view;
    }

    private void initViewPager(View view) {
        width = getResources().getDisplayMetrics().widthPixels;//获取手机屏幕大小
        newWidth = (int) (discoverFragmentModel.divideWidth(width, 1080, 6) * 17);
        padding = (int) (discoverFragmentModel.divideWidth(width, 1080, 6) * 9);
        initDots();// 初始化圆点
        initImages();// 初始化图片
        mViewPager.setOnPageChangeListener(this);
    }

    private void initImages() {
        mImageViews = new ImageView[2][];

        mImageViews[0] = new ImageView[mImageRes.length];

        mImageViews[1] = new ImageView[mImageRes.length];

        for (int i = 0; i < mImageViews.length; i++) {

            for (int j = 0; j < mImageViews[i].length; j++) {

                ImageView iv = new ImageView(getActivity());

                iv.setBackgroundResource(mImageRes[j]);

                mImageViews[i][j] = iv;

            }

        }

        discoverViewPagerAdapter = new DiscoverViewPagerAdapter(mImageViews, mImageRes);

        mViewPager.setAdapter(discoverViewPagerAdapter);

        mViewPager.setCurrentItem(mImageRes.length * 50);

        mHandler.sendEmptyMessageDelayed(AUTO, delay);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {

            switch (msg.what) {
                case AUTO:

                    int index = mViewPager.getCurrentItem();

                    mViewPager.setCurrentItem(index + 1);

                    mHandler.sendEmptyMessageDelayed(AUTO, delay);

                    break;

                default:
                    break;
            }

        }

    };

    private void initDots() {
        mDots = new ImageView[mImageRes.length];

        for (int i = 0; i < mImageRes.length; i++) {

            ImageView iv = new ImageView(getActivity());

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    newWidth, newWidth);

            lp.leftMargin = padding;

            lp.rightMargin = padding;

            lp.topMargin = padding;

            lp.bottomMargin = padding;

            iv.setLayoutParams(lp);

            iv.setImageResource(R.mipmap.banner_point_default);

            dotLinearLayout.addView(iv);

            mDots[i] = iv;

        }
        mDots[0].setImageResource(R.mipmap.banner_point_hov);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentDot(position % mImageRes.length);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //当前位置圆点图片
    private void setCurrentDot(int currentPosition) {
        for (int i = 0; i < mDots.length; i++) {

            if (i == currentPosition) {

                mDots[i].setImageResource(R.mipmap.banner_point_hov);

            } else {

                mDots[i].setImageResource(R.mipmap.banner_point_default);

            }
        }
    }
}
