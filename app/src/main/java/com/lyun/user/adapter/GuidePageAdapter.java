package com.lyun.user.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 郑成裕 on 2017/3/14.
 */

public class GuidePageAdapter extends PagerAdapter {
    private List<ImageView> mViewList;

    public GuidePageAdapter(List<ImageView> mViewList) {
        this.mViewList = mViewList;
    }

    //获得页面的个数
    @Override
    public int getCount() {
        return mViewList.size();
    }

    //判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //初始化position位置的界面
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
         container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    //对不在界面内的导航页进行删除
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }
}
