package cn.com.law_cloud.user.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.math.BigDecimal;

import butterknife.BindView;
import cn.com.law_cloud.fragment.BaseFragment;
import cn.com.law_cloud.user.R;
import cn.com.law_cloud.user.adapter.DiscoverRecyclerAdapter;
import cn.com.law_cloud.user.adapter.DiscoverViewPagerAdapter;
import cn.com.law_cloud.user.entity.DiscoverRecyclerViewMemeber;

public class DiscoverFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.viewPager_discover)
    ViewPager mViewPager;
    @BindView(R.id.dotLinearLayout)
    LinearLayout dotLinearLayout;//圆点布局
    @BindView(R.id.recyclerView_discover)
    RecyclerView mRecyclerView;
    @BindView(R.id.scrollView_discover)
    ScrollView mScrollView;

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
    private DiscoverRecyclerViewMemeber mDiscoverRecyclerViewMemeber = new DiscoverRecyclerViewMemeber();

    public DiscoverFragment() {
        // Required empty public constructor
    }

    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        initViewPager(view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_discover);
        mDiscoverRecyclerViewMemeber.initRecyclerView();
        discoverRecyclerViewAdapter = new DiscoverRecyclerAdapter(getActivity(), mDiscoverRecyclerViewMemeber);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);//设置布局管理器
        mRecyclerView.setAdapter(discoverRecyclerViewAdapter);
        mScrollView = (ScrollView) view.findViewById(R.id.scrollView_discover);
//        mScrollView.smoothScrollTo(0, 20);
        return view;
    }


    private void initViewPager(View view) {
        width = getResources().getDisplayMetrics().widthPixels;//获取手机屏幕大小
        newWidth = (int) (divideWidth(width, 1080, 6) * 17);
        padding = (int) (divideWidth(width, 1080, 6) * 9);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager_discover);
        dotLinearLayout = (LinearLayout) view.findViewById(R.id.dotLinearLayout);
        mViewPager.setOnPageChangeListener(this);

        initDots();// 初始化圆点
        initImages();// 初始化图片


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

    private double divideWidth(int screenWidth, int picWidth, int retainValue) {
        BigDecimal screenBD = new BigDecimal(Double.toString(screenWidth));
        BigDecimal picBD = new BigDecimal(Double.toString(picWidth));
        return screenBD.divide(picBD, retainValue, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
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
