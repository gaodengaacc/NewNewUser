package cn.com.law_cloud.user.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.com.law_cloud.adapter.FragmentPagerBaseAdapter;

/**
 * Created by ZHAOWEIWEI on 2016/7/11.
 */
public class MainPagerAdapter extends FragmentPagerBaseAdapter {

    public MainPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments) {
        super(context, fm, fragments);
    }

}
