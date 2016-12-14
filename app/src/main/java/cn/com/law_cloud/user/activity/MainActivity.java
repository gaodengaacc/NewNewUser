package cn.com.law_cloud.user.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.law_cloud.activity.BaseActivity;
import cn.com.law_cloud.user.entity.TabItemBean;
import cn.com.law_cloud.user.R;
import cn.com.law_cloud.user.adapter.MainPagerAdapter;
import cn.com.law_cloud.user.fragment.DiscoverFragment;
import cn.com.law_cloud.user.fragment.SpecialistTranslationFragment;
import cn.com.law_cloud.user.fragment.UserCenterFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tab)
    TabLayout mTab;
    @BindView(R.id.main_container)
    ViewPager mViewPager;

    private FragmentManager mFragmentManager;

    private DiscoverFragment mDiscoverFragment;
    private SpecialistTranslationFragment mSpecialistTranslationFragment;
    private UserCenterFragment mUserCenterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDiscoverFragment = DiscoverFragment.newInstance();
        mSpecialistTranslationFragment = SpecialistTranslationFragment.newInstance();
        mUserCenterFragment = UserCenterFragment.newInstance();

        mFragmentManager = getSupportFragmentManager();

        mTab.setTabMode(TabLayout.MODE_FIXED);
        mTab.setupWithViewPager(mViewPager);
        mTab.setTabGravity(TabLayout.GRAVITY_FILL);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(mDiscoverFragment);
        fragments.add(mSpecialistTranslationFragment);
        fragments.add(mUserCenterFragment);
        mViewPager.setAdapter(new MainPagerAdapter(this, mFragmentManager, fragments));

        List<TabItemBean> tabs = new ArrayList<>();
        tabs.add(new TabItemBean(R.mipmap.ic_launcher, "发现", R.layout.item_main_tab));
        tabs.add(new TabItemBean(R.mipmap.ic_launcher, "专人翻译", R.layout.item_main_tab));
        tabs.add(new TabItemBean(R.mipmap.ic_launcher, "我", R.layout.item_main_tab));

        for (int i = 0; i < 3; i++) {
            TabLayout.Tab tab = mTab.getTabAt(i);
            tab.setCustomView(tabs.get(i).getTabView(this));
        }

        mTab.addOnTabSelectedListener(onTabSelectedListener);
        mTab.getTabAt(tabIndex).select();

    }


    private int tabIndex = 0;

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
