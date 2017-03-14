package com.lyun.user.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;
import com.lyun.user.R;
import com.lyun.user.adapter.GuidePageAdapter;

import net.funol.databinding.watchdog.annotations.WatchThis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郑成裕 on 2017/3/14.
 */

public class GuideViewModel extends ViewModel {
    private Context context;
    public final ObservableField<PagerAdapter> pageAdapter = new ObservableField<>();
    public final ObservableField<ViewPager.OnPageChangeListener> pageChange = new ObservableField<>();
    public final ObservableField<View.OnTouchListener> onTouch = new ObservableField<>();
    private List<ImageView> mViewList;
    private ImageView imageView;
    private GuidePageAdapter adapter;
    private int currentItem;
    private int[] guideImages = {R.mipmap.guide_page1, R.mipmap.guide_page2, R.mipmap.guide_page3};


    @WatchThis
    public final BaseObservable skipResult = new BaseObservable();


    public GuideViewModel(Context context) {
        this.context = context;
        mViewList = new ArrayList<>();
        initData();
        adapter = new GuidePageAdapter(mViewList);
        pageAdapter.set(adapter);
        pageChange.set(pageChangeListener);
        onTouch.set(touchListener);

    }

    public RelayCommand onSkip = new RelayCommand(() -> {
        skipResult.notifyChange();
    });


    private void initData() {
        for (int imgIds : guideImages) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imgIds);
            mViewList.add(imageView);
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        float startX;
        float startY;
        float endX;
        float endY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    startY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    endX = event.getX();
                    endY = event.getY();
                    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                    //获取屏幕的宽度
                    Point size = new Point();
                    windowManager.getDefaultDisplay().getSize(size);
                    int width = size.x;
                    //首先要确定的是，是否到了最后一页，然后判断是否向左滑动，并且滑动距离是否符合，我这里的判断距离是屏幕宽度的4分之一（这里可以适当控制）
                    if (currentItem == (mViewList.size() - 1) && startX - endX > 0 && startX - endX >= (width / 4)) {
                        skipResult.notifyChange();
                    }
                    break;
            }
            return false;
        }
    };

}
