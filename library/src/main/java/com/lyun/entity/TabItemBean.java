package com.lyun.entity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

/**
 * 主页tab的item
 */
public class TabItemBean {

    private int iconRes;
    private String name;

    private int layout;

    public TabItemBean(int iconRes, String name, int layout) {
        this.iconRes = iconRes;
        this.name = name;
        this.layout = layout;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public View getTabView(Context context) {
        CheckBox view = (CheckBox) LayoutInflater.from(context).inflate(getLayout(), null);
        view.setText(getName());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawablesWithIntrinsicBounds(drawables[0], getCompoundDrawable(context, getIconRes()), drawables[2], drawables[3]);
        return view;
    }

    /**
     * 从资源id获取Drawable
     *
     * @param resId
     * @return
     */
    protected Drawable getCompoundDrawable(Context context, int resId) {
        if (resId == 0) {
            return null;
        }
        try {
            return context.getResources().getDrawable(resId);
        } catch (Exception e) {
            return null;
        }
    }
}
