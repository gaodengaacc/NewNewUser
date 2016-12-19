package com.lyun.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHAOWEIWEI on 2016/7/18.
 */
public class FragmentPagerBaseAdapter extends FragmentPagerAdapter {

    protected Context mContext;
    protected List<Fragment> mFragments;

    public FragmentPagerBaseAdapter(Context context, FragmentManager fm) {
        this(context, fm, null);
    }

    public FragmentPagerBaseAdapter(Context context, FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mContext = context;
        this.mFragments = fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.mFragments = mFragments;
    }

    public void addFragments(List<Fragment> fragments) {
        if (mFragments == null)
            mFragments = new ArrayList<Fragment>();
        mFragments.addAll(fragments);
    }

    public void clearAllFragments() {
        if (mFragments != null)
            mFragments.clear();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        if (mFragments == null)
            return 0;
        return mFragments.size();
    }
}
