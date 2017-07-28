package com.lyun.user.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.view.PagerAdapter;

import com.lyun.library.mvvm.viewmodel.ViewModel;
/**
 * @author Gordon
 * @since 2016/12/30
 * do(主Activity 处理类)
 */
public class MainActivityViewModel extends ViewModel {

    public final ObservableInt currentItem = new ObservableInt(0);
    public final ObservableField<PagerAdapter> pagerAdapter = new ObservableField<>();

    public MainActivityViewModel(PagerAdapter pagerAdapter) {
        this.pagerAdapter.set(pagerAdapter);
    }

}
