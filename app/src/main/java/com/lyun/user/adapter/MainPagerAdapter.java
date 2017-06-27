package com.lyun.user.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.lyun.adapter.FragmentPagerBaseAdapter;

import java.util.List;

/**
 * Created by ZHAOWEIWEI on 2016/7/11.
 */
public class MainPagerAdapter extends FragmentPagerBaseAdapter {

    public MainPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments) {
        super(context, fm, fragments);
    }

}
