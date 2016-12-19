package cn.com.law_cloud.user.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by 郑成裕 on 2016/12/15.
 */

public class DiscoverViewPagerAdapter extends PagerAdapter {
    private ImageView[][] mImageViews;

    private int[] mImageRes;

    public DiscoverViewPagerAdapter(ImageView[][] imageViews, int[] mImageRes) {
        this.mImageViews = imageViews;
        this.mImageRes = mImageRes;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    //判断是否为同一张照片
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mImageRes.length == 1) {
            return mImageViews[position / mImageRes.length % 2][0];
        } else {
            ((ViewPager) container).addView(mImageViews[position
                    / mImageRes.length % 2][position % mImageRes.length], 0);
        }
        return mImageViews[position / mImageRes.length % 2][position
                % mImageRes.length];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mImageRes.length == 1) {
            ((ViewPager) container).removeView(mImageViews[position
                    / mImageRes.length % 2][0]);
        } else {
            ((ViewPager) container).removeView(mImageViews[position
                    / mImageRes.length % 2][position % mImageRes.length]);
        }
    }

}
