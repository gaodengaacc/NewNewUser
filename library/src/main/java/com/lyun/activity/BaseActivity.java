package com.lyun.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.lyun.utils.ToastUtil;
import com.lyun.widget.dialog.ProgressBarDialog;
import com.lyun.widget.refresh.PullToRefreshLayout;


/**
 * 任何Activity必须继承自该Activity
 *
 * @author 赵尉尉
 * @since 2015/5/13 16:16
 */
public class BaseActivity extends FragmentActivity  {

    protected BaseApplication application;
    protected ProgressBarDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        application = (BaseApplication) getApplication();
        initViews();
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
        // 友盟统计
    }

    protected static int STOP_ALL = -1;
    protected static int STOP_REFRESH = 1;
    protected static int STOP_LOADMORE = 2;
    protected static int REFRESH_SUCCEED = PullToRefreshLayout.SUCCEED;
    protected static int REFRESH_FAIL = PullToRefreshLayout.FAIL;

    /**
     * 停止刷新
     *
     * @param refreshLayout 要停止刷新的PullToRefreshLayout
     * @param stopWhat      {@link #STOP_ALL} 停止上拉和下拉 {@link #STOP_REFRESH} 停止刷新 {@link #STOP_LOADMORE} 停止加载更多
     * @param refreshResult {@link #REFRESH_SUCCEED} 刷新成功 {@link #REFRESH_FAIL}刷新失败
     */
    protected void stopRefreshOrLoadMore(PullToRefreshLayout refreshLayout, int stopWhat, int refreshResult) {
        if (refreshLayout == null) {
            return;
        }
        if (stopWhat == STOP_REFRESH) {
            refreshLayout.refreshFinish(refreshResult);
        } else if (stopWhat == STOP_LOADMORE) {
            refreshLayout.loadmoreFinish(refreshResult);
        } else if (stopWhat == STOP_ALL) {
            refreshLayout.refreshFinish(refreshResult);
            refreshLayout.loadmoreFinish(refreshResult);
        }
    }


    protected void showActionSheet() {
    }


    public void toast(String content, int iconType) {
        if (!TextUtils.isEmpty(content)) {
            ToastUtil.showTips(this, iconType, content);
        }
    }


    public void openActivity(Class clazz) {
        openActivity(null, clazz);
    }

    public void openActivity(Bundle pBundle, Class clazz) {
        Intent intent = new Intent(this, clazz);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    protected void initViews() {
        progressBar = new ProgressBarDialog(this);
        progressBar.setCanceledOnTouchOutside(false);
    }

    public void show() {
        if (progressBar != null && !progressBar.isShowing()) {
            progressBar.show();
        }
    }

    public void setCancelListener(ProgressBarDialog.LoadingCancelCallBack cancelListener) {
        progressBar.setLoadingCancel(cancelListener);
    }

    public void show(String loadingText) {
        if (progressBar != null && !progressBar.isShowing()) {
            progressBar.show();
            progressBar.setMessage(loadingText);
        }
    }

    public void dismiss() {
        if (progressBar != null && progressBar.isShowing()) {
            progressBar.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressBar = null;
    }
}