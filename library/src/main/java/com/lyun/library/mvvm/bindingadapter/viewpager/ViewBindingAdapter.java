package com.lyun.library.mvvm.bindingadapter.viewpager;

import android.databinding.BindingAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lyun.library.mvvm.command.RelayCommand;

public class ViewBindingAdapter {
    @BindingAdapter(value = {"onPageScrolledCommand", "onPageSelectedCommand", "onPageScrollStateChangedCommand"}, requireAll = false)
    public static void onScrollChangeCommand(final ViewPager viewPager,
                                             final RelayCommand<ViewPagerDataWrapper> onPageScrolledCommand,
                                             final RelayCommand<Integer> onPageSelectedCommand,
                                             final RelayCommand<Integer> onPageScrollStateChangedCommand) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int state;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (onPageScrolledCommand != null) {
                    onPageScrolledCommand.execute(new ViewPagerDataWrapper(position, positionOffset, positionOffsetPixels, state));
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (onPageSelectedCommand != null) {
                    onPageSelectedCommand.execute(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                this.state = state;
                if (onPageScrollStateChangedCommand != null) {
                    onPageScrollStateChangedCommand.execute(state);
                }
            }
        });

    }

    @BindingAdapter("adapter")
    public static void setAdapter(ViewPager viewPage, RelayCommand<ViewPager> relayCommand) {
        relayCommand.execute(viewPage);
    }

    public static class ViewPagerDataWrapper {
        public float positionOffset;
        public float position;
        public int positionOffsetPixels;
        public int state;

        public ViewPagerDataWrapper(float position, float positionOffset, int positionOffsetPixels, int state) {
            this.positionOffset = positionOffset;
            this.position = position;
            this.positionOffsetPixels = positionOffsetPixels;
            this.state = state;
        }
    }

    @BindingAdapter("setAdapter")
    public static void setViewPagerAdapter(ViewPager viewPager, PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter("pageChange")
    public static void setPageChangeListener(ViewPager viewPager, ViewPager.OnPageChangeListener pageChangeListener) {
        viewPager.setOnPageChangeListener(pageChangeListener);
    }

    @BindingAdapter("touchListener")
    public static void setTouchListener(ViewPager viewPager, View.OnTouchListener touchListener) {
        viewPager.setOnTouchListener(touchListener);
    }
}
