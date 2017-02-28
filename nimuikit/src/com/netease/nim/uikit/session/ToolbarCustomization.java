package com.netease.nim.uikit.session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.netease.nim.uikit.model.ToolBarOptions;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZHAOWEIWEI on 2017/2/24.
 */

public class ToolbarCustomization implements Serializable {

    private ToolBarOptions toolBarOptions;

    /**
     * ActionBar右侧可定制按钮。默认为空。
     */
    private List<OptionsButton> optionsButtons;

    /**
     * ActionBar 右侧按钮，可定制icon和点击事件
     */
    public static abstract class OptionsButton implements Serializable {

        // 图标drawable id
        private int iconId;

        public int getIconId() {
            return iconId;
        }

        public void setIconId(int iconId) {
            this.iconId = iconId;
        }

        // 响应事件
        public abstract void onClick(Context context, View view, String sessionId);
    }

    /**
     * 如果OptionsButton的点击响应中需要startActivityForResult，可在此函数中处理结果。
     * 需要注意的是，由于加号中的Action的限制，RequestCode只能使用int的最低8位。
     *
     * @param activity    当前的聊天Activity
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        返回的结果数据
     */
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
    }

    public List<OptionsButton> getOptionsButtons() {
        return optionsButtons;
    }

    public void setOptionsButtons(List<OptionsButton> optionsButtons) {
        this.optionsButtons = optionsButtons;
    }

    public ToolBarOptions getToolBarOptions() {
        return toolBarOptions;
    }

    public void setToolBarOptions(ToolBarOptions toolBarOptions) {
        this.toolBarOptions = toolBarOptions;
    }
}
