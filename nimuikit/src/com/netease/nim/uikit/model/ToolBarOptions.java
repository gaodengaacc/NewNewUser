package com.netease.nim.uikit.model;

import com.netease.nim.uikit.R;

import java.io.Serializable;

/**
 *
 * Created by hzxuwen on 2016/6/16.
 */
public class ToolBarOptions implements Serializable {
    /**
     * toolbar的title资源id
     */
    private int titleId = 0;
    /**
     * toolbar的title
     */
    private String titleString;
    /**
     * toolbar的logo资源id
     */
    private int logoId = R.drawable.nim_actionbar_nest_dark_logo;
    /**
     * toolbar的返回按钮资源id，默认开启的资源nim_actionbar_dark_back_icon
     */
    private int navigateId = R.drawable.nim_actionbar_dark_back_icon;
    /**
     * toolbar的返回按钮，默认开启
     */
    private boolean needNavigate = true;

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public String getTitleString() {
        return titleString;
    }

    public void setTitleString(String titleString) {
        this.titleString = titleString;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public int getNavigateId() {
        return navigateId;
    }

    public void setNavigateId(int navigateId) {
        this.navigateId = navigateId;
    }

    public boolean isNeedNavigate() {
        return needNavigate;
    }

    public void setNeedNavigate(boolean needNavigate) {
        this.needNavigate = needNavigate;
    }
}
