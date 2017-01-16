package com.lyun.library.mvvm.bindingadapter.tablayout;

import android.databinding.BindingAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.lyun.entity.TabItemBean;

import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/27
 * do()
 */

public class ViewBindAdapter {
    @BindingAdapter("viewPage")
    public static void setupWithViewPager(TabLayout tabLayout, ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
    }

    @BindingAdapter("tabSelectListener")
    public static void setTabSelectListener(TabLayout tabLayout, TabLayout.OnTabSelectedListener listener) {
        tabLayout.addOnTabSelectedListener(listener);
    }

    @BindingAdapter("initTab")
    public static void initTab(TabLayout tabLayout, TabData data) {
        if (data.getTabs() != null){
            for (int i = 0; i < data.getTabs().size(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setCustomView(data.getTabs().get(i).getTabView(tabLayout.getContext()));
            }
            tabLayout.getTabAt(data.getIndex()).select();//进入APP后显示的“专人翻译”
        }
    }

    public static class TabData {
        public List<TabItemBean> getTabs() {
            return tabs;
        }

        public void setTabs(List<TabItemBean> tabs) {
            this.tabs = tabs;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        private List<TabItemBean> tabs;
        private int index;
    }
}
