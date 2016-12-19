package com.lyun.user.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lyun.activity.BaseActivity;
import com.lyun.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 全局自定义TitleBarActivity
 * 使用全局TitleBar的Activity必须集成自该Activity
 * 设置TitleBar控件是否显示时使用INVISIABLE，不要使用GONE
 *
 * @author 赵尉尉
 * @since 2015/5/13 16:24
 */
@SuppressWarnings("deprecation")
public abstract class GlobalTitleBarActivity extends BaseActivity {

    /**
     * 标题栏返回按钮
     */
     @BindView(R.id.title_back)
     TextView mTitleBack;
    /**
     * 标题栏标题
     */
    @BindView(R.id.title_title)
    protected TextView mTitleTitle;
    /**
     * 标题栏功能按钮
     */
    @BindView(R.id.title_fuction)
    protected TextView mTitleFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_custom_title);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_global);
        ButterKnife.bind(this);
        clickListener();
    }

    private void clickListener() {
        mTitleBack.setOnClickListener(mTitleOnClickListener);
        mTitleFunction.setOnClickListener(mTitleOnClickListener);
        mTitleTitle.setOnClickListener(mTitleOnClickListener);
    }
    private View.OnClickListener mTitleOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.title_back) {
                onBackClick(v);
            } else if (v.getId() == R.id.title_title) {
                onTitleClick(v);
            } else if (v.getId() == R.id.title_fuction) {
                onFunctionClick(v);
            }
        }
    };
    /**
     * 标题栏返回按钮被点击时调用该方法
     *
     * @param view 返回按钮
     */
    protected abstract void onBackClick(View view);

    /**
     * 标题栏标题被点击时调用该方法
     *
     * @param view 标题
     */
    protected abstract void onTitleClick(View view);

    /**
     * 标题栏功能按钮被点击时调用该方法
     *
     * @param view 功能按钮
     */
    protected abstract void onFunctionClick(View view);

    /**
     * 点击返回键时触发
     * 如不需要与TitleBar点击触发同一事件请重写此方法
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isUseSameBackFuction()) {
            onBackClick(mTitleBack);
        }
    }

    /**
     * 如不需要与TitleBar点击触发同一事件请重写此方法并返回false
     *
     * @return boolean 是否与TitleBar返回点击使用同一处理方法
     */
    protected boolean isUseSameBackFuction() {
        return true;
    }

    /**
     * 将TitleBar主题设置为白色
     */
    protected void setTitleWhiteTheme() {
//        setTitleBackCompoundDrawable(R.drawable.ic_back_blue, 0, 0, 0);
//        setTitleBackgroudColor(Color.parseColor("#f7f7f7"));
//        mTitleTitle.setTextColor(Color.parseColor("#1588ff"));
//        mTitleBack.setTextColor(Color.parseColor("#1588ff"));
//        mTitleFunction.setTextColor(Color.parseColor("#1588ff"));
    }

    /**
     * 设置标题栏背景色
     *
     * @param color 背景色
     */
//    protected void setTitleBackgroudColor(int color) {
//
//        mTitleContainer.setBackgroundColor(color);
//    }

    /**
     * 设置标题栏返回键背景
     *
     * @param drawable Drawable
     */
    protected void setTitleBackBackgroudDrawable(Drawable drawable) {
        mTitleBack.setBackgroundDrawable(drawable);
    }

    /**
     * 设置标题栏返回键背景
     *
     * @param resId 资源标识
     */
    protected void setTitleBackBackgroudDrawable(int resId) {
        mTitleBack.setBackgroundDrawable(getResources().getDrawable(resId));
    }

    /**
     * 从资源id获取Drawable
     *
     * @param resId
     * @return
     */
    protected Drawable getCompoundDrawable(int resId) {
        if (resId == 0) {
            return null;
        }
        try {
            return getResources().getDrawable(resId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 设置返回按钮的drawableLeft,drawableTop,drawableRight,drawableBootom
     * drawable为空时传入0即可
     *
     * @param leftResId
     * @param topResId
     * @param rightResId
     * @param bottomResId
     */
    protected void setTitleBackCompoundDrawable(int leftResId, int topResId, int rightResId, int bottomResId) {
        setTitleBackCompoundDrawable(getCompoundDrawable(leftResId), getCompoundDrawable(topResId), getCompoundDrawable(rightResId), getCompoundDrawable(bottomResId));
    }

    /**
     * 设置返回按钮的drawableLeft,drawableTop,drawableRight,drawableBootom
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    protected void setTitleBackCompoundDrawable(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        mTitleBack.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    /**
     * 设置标题按钮的drawableLeft,drawableTop,drawableRight,drawableBootom
     * drawable为空时传入0即可
     *
     * @param leftResId
     * @param topResId
     * @param rightResId
     * @param bottomResId
     */
    protected void setTitleTitleCompoundDrawable(int leftResId, int topResId, int rightResId, int bottomResId) {
        setTitleTitleCompoundDrawable(getCompoundDrawable(leftResId), getCompoundDrawable(topResId), getCompoundDrawable(rightResId), getCompoundDrawable(bottomResId));
    }

    /**
     * 设置标题的drawableLeft,drawableTop,drawableRight,drawableBootom
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    protected void setTitleTitleCompoundDrawable(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        mTitleTitle.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    /**
     * 设置标题按钮的drawableLeft,drawableTop,drawableRight,drawableBootom
     * drawable为空时传入0即可
     *
     * @param leftResId
     * @param topResId
     * @param rightResId
     * @param bottomResId
     */
    protected void setTitleFunctionCompoundDrawable(int leftResId, int topResId, int rightResId, int bottomResId) {
        setTitleFunctionCompoundDrawable(getCompoundDrawable(leftResId), getCompoundDrawable(topResId), getCompoundDrawable(rightResId), getCompoundDrawable(bottomResId));
    }

    /**
     * 设置标题的drawableLeft,drawableTop,drawableRight,drawableBootom
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    protected void setTitleFunctionCompoundDrawable(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        mTitleFunction.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

}
