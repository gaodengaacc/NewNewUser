package com.netease.nim.uikit.session;

import java.io.Serializable;

/**
 * Created by ZHAOWEIWEI on 2017/2/24.
 */

public class MessagePanelCustomization implements Serializable {

    /**
     * 聊天背景。优先使用uri，如果没有提供uri，使用color。如果没有color，使用默认。uri暂时支持以下格式：<br>
     * drawable: android.resource://包名/drawable/资源名
     * assets: file:///android_asset/{asset文件路径}
     * file: file:///文件绝对路径
     */
    private String backgroundUri;
    private int backgroundColor;
    // 长按消息操作
    private boolean msgLongClickEnabled;

    public String getBackgroundUri() {
        return backgroundUri;
    }

    public void setBackgroundUri(String backgroundUri) {
        this.backgroundUri = backgroundUri;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isMsgLongClickEnabled() {
        return msgLongClickEnabled;
    }

    public void setMsgLongClickEnabled(boolean msgLongClickEnabled) {
        this.msgLongClickEnabled = msgLongClickEnabled;
    }
}
