package com.lyun.user.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.CheckBox;

import com.lyun.entity.TabItemBean;
import com.lyun.library.mvvm.bindingadapter.tablayout.ViewBindAdapter;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.adapter.MainPagerAdapter;
import com.lyun.user.fragment.DiscoverFragment;
import com.lyun.user.fragment.SpecialistTranslationFragment;
import com.lyun.user.fragment.UserCenterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gordon
 * @since 2016/12/30
 * do()
 */

public class MainActivityViewModel extends ViewModel {
    public final ObservableField<ViewPager> viewPage = new ObservableField<>();
    public final ObservableField<PagerAdapter> viewPageAdapter = new ObservableField<>();
    public final ObservableField<TabLayout.OnTabSelectedListener> tabListener = new ObservableField<>();
    public final ObservableField<ViewBindAdapter.TabData> tabData = new ObservableField<>();
    public final ObservableField<Boolean> showWidow = new ObservableField<>();
    private FragmentManager mFragmentManager;
    private DiscoverFragment mDiscoverFragment;
    private SpecialistTranslationFragment mSpecialistTranslationFragment;
    private UserCenterFragment mUserCenterFragment;
    private int tabIndex = 0;

    public MainActivityViewModel(Context context, ViewPager viewPager, FragmentManager mFragmentManager) {
        super(context);
        this.viewPage.set(viewPager);
        this.mFragmentManager = mFragmentManager;
        init();
    }

    public void init() {
        mDiscoverFragment = DiscoverFragment.newInstance();
        mSpecialistTranslationFragment = SpecialistTranslationFragment.newInstance();
        mUserCenterFragment = UserCenterFragment.newInstance();
        List<Fragment> fragments = new ArrayList<>();
        //fragments.add(mDiscoverFragment);
        fragments.add(mSpecialistTranslationFragment);
        fragments.add(mUserCenterFragment);
        viewPageAdapter.set(new MainPagerAdapter(getContext(), mFragmentManager, fragments));
        List<TabItemBean> tabs = new ArrayList<>();
        //tabs.add(new TabItemBean(R.drawable.btn_find_selector, "发现", R.layout.item_main_tab));
        tabs.add(new TabItemBean(R.drawable.btn_translator_selector, "专人翻译", R.layout.item_main_tab));
        tabs.add(new TabItemBean(R.drawable.btn_me_selector, "我", R.layout.item_main_tab));
        tabListener.set(onTabSelectedListener);
        ViewBindAdapter.TabData tabData = new ViewBindAdapter.TabData();
        tabData.setTabs(tabs);
        tabData.setIndex(tabIndex);
        this.tabData.set(tabData);
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            try {
                ((CheckBox) tab.getCustomView()).setChecked(true);
                tabIndex = tab.getPosition();
            } catch (Exception e) {

            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            try {
                ((CheckBox) tab.getCustomView()).setChecked(false);
            } catch (Exception e) {

            }
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            try {
                ((CheckBox) tab.getCustomView()).setChecked(true);
                tabIndex = tab.getPosition();
            } catch (Exception e) {

            }
        }
    };

}
