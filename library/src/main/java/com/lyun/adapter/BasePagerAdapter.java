package com.lyun.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHAOWEIWEI on 2017/7/31.
 */

public abstract class BasePagerAdapter<D> extends PagerAdapter {

    protected List<D> data;
    protected List<String> mPageTitles;

    public BasePagerAdapter(List<D> data, List<String> titles) {
        this.data = data;
        this.mPageTitles = titles;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(position, container, getItem(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public String getPageTitle(int position) {
        if (mPageTitles == null || mPageTitles.size() <= position)
            return "";
        return mPageTitles.get(position);
    }


    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public D getItem(int position) {
        return data.get(position);
    }

    protected abstract View getView(int position, ViewGroup parent, D data);


    public void addAll(List<D> data) {
        this.data = new ArrayList<>(data);
        notifyDataSetChanged();
    }

}
