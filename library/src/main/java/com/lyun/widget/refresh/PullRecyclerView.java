package com.lyun.widget.refresh;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class PullRecyclerView extends RecyclerView implements Pullable {
    private int firstVisiblePosition;
    private int lastVisiblePosition;
    public PullRecyclerView(Context context) {
        super(context);
    }

    public PullRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init() {
        if (getLayoutManager() instanceof LinearLayoutManager) {
            firstVisiblePosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            lastVisiblePosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            firstVisiblePosition = ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            lastVisiblePosition = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        }
    }

    @Override
    public boolean canPullDown() {
        init();
        if (getLayoutManager().getChildCount() == 0) {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (firstVisiblePosition == 0 && getChildAt(0).getTop() >= 0) {
            // 滑到ListView的顶部了
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp() {
        init();
        int childCount = getLayoutManager().getItemCount();
        if (childCount== 0) {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (lastVisiblePosition == (childCount - 1)) {
            // 滑到底部了
            if (getChildAt(lastVisiblePosition -firstVisiblePosition) != null && getChildAt(lastVisiblePosition - firstVisiblePosition).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }
}