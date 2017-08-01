package com.lyun.library.mvvm.bindingadapter;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.adapters.ListenerUtil;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lyun.library.R;
import com.lyun.library.mvvm.command.RelayCommand;

public class ViewPagerBindingAdapter {
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
    public static void setAdapter(ViewPager viewPager, PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter("offscreenPageLimit")
    public static void setOffscreenPageLimit(ViewPager viewPager, int limit) {
        viewPager.setOffscreenPageLimit(limit);
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

    @BindingAdapter("pageChange")
    public static void setPageChangeListener(ViewPager viewPager, ViewPager.OnPageChangeListener pageChangeListener) {
        viewPager.setOnPageChangeListener(pageChangeListener);
    }

    @BindingAdapter("touchListener")
    public static void setTouchListener(ViewPager viewPager, View.OnTouchListener touchListener) {
        viewPager.setOnTouchListener(touchListener);
    }

    @BindingAdapter("currentItem")
    public static void setCurrentItem(ViewPager viewPager, int index) {
        final int oldIndex = viewPager.getCurrentItem();
        if (index == oldIndex) {
            return;
        }
        viewPager.setCurrentItem(index);
    }

    @InverseBindingAdapter(attribute = "currentItem", event = "currentItemAttrChanged")
    public static int getCurrentItemInt(ViewPager view) {
        return view.getCurrentItem();
    }

    @BindingAdapter(value = {"onPageScrolled", "onPageSelected",
            "onPageScrollStateChanged", "currentItemAttrChanged"}, requireAll = false)
    public static void addOnPageChangeListener(ViewPager view, final onPageScrolled pageScrolled,
                                               final onPageSelected pageSelected, final onPageScrollStateChanged pageScrollStateChanged,
                                               final InverseBindingListener currentItemAttrChanged) {
        final ViewPager.OnPageChangeListener newValue;
        if (pageScrolled == null && pageSelected == null && pageScrollStateChanged == null && currentItemAttrChanged == null) {
            newValue = null;
        } else {
            newValue = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (pageScrolled != null) {
                        pageScrolled.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }
                }

                @Override
                public void onPageSelected(int position) {
                    if (pageSelected != null) {
                        pageSelected.onPageSelected(position);
                    }
                    if (currentItemAttrChanged != null) {
                        currentItemAttrChanged.onChange();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (pageScrollStateChanged != null) {
                        pageScrollStateChanged.onPageScrollStateChanged(state);
                    }
                }
            };
        }
        final ViewPager.OnPageChangeListener oldValue = ListenerUtil.trackListener(view, newValue, R.id.onPageChangeListener);
        if (oldValue != null) {
            view.removeOnPageChangeListener(oldValue);
        }
        if (newValue != null) {
            view.addOnPageChangeListener(newValue);
        }
    }

    public interface onPageScrolled {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
    }

    public interface onPageSelected {
        void onPageSelected(int position);
    }

    public interface onPageScrollStateChanged {
        void onPageScrollStateChanged(int state);
    }
}
