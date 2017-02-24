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
    public boolean withSticker;
    public boolean showAudioInputBar;
    public boolean showEmojiInputBar;

    @DrawableRes
    public int messageInputBoxBackgroud;

    /**
     * 加号展开后的action list。
     * 默认已包含图片，视频和地理位置
     */
    public ArrayList<BaseAction> actions;

    // uikit内建了对贴图消息的输入和管理展示，并和emoji表情整合在了一起，但贴图消息的附件定义开发者需要根据自己的扩展
    public MsgAttachment createStickerAttachment(String category, String item) {
        return null;
    }

}
