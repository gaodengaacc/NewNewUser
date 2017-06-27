package com.netease.nim.uikit.session.module.input;

import android.support.annotation.DrawableRes;

import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 聊天界面输入栏定制化参数。 可定制：<br>
 * 1. 输入栏上面的按钮
 * 2. 加号展开后的按钮和动作 <br>
 */
public class InputPanelCustomization implements Serializable {

    // UIKit
    private boolean withSticker;
    private boolean showAudioInputBar;
    private boolean showEmojiInputBar;
    @DrawableRes
    private int msgInputBoxBackgroud;

    /**
     * 加号展开后的action list。
     * 默认已包含图片，视频和地理位置
     */
    private ArrayList<BaseAction> actions;

    // uikit内建了对贴图消息的输入和管理展示，并和emoji表情整合在了一起，但贴图消息的附件定义开发者需要根据自己的扩展
    public MsgAttachment createStickerAttachment(String category, String item) {
        return null;
    }

    public boolean isWithSticker() {
        return withSticker;
    }

    public void setWithSticker(boolean withSticker) {
        this.withSticker = withSticker;
    }

    public boolean isShowAudioInputBar() {
        return showAudioInputBar;
    }

    public void setShowAudioInputBar(boolean showAudioInputBar) {
        this.showAudioInputBar = showAudioInputBar;
    }

    public boolean isShowEmojiInputBar() {
        return showEmojiInputBar;
    }

    public void setShowEmojiInputBar(boolean showEmojiInputBar) {
        this.showEmojiInputBar = showEmojiInputBar;
    }

    public int getMsgInputBoxBackgroud() {
        return msgInputBoxBackgroud;
    }

    public void setMsgInputBoxBackgroud(int msgInputBoxBackgroud) {
        this.msgInputBoxBackgroud = msgInputBoxBackgroud;
    }

    public ArrayList<BaseAction> getActions() {
        return actions;
    }

    public void setActions(ArrayList<BaseAction> actions) {
        this.actions = actions;
    }
}
