package com.netease.nim.uikit.custom;

import com.netease.nim.uikit.session.SessionCustomization;
import com.netease.nim.uikit.session.module.input.InputPanelCustomization;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;

/**
 * SessionCustomization 可以实现聊天界面定制项：
 * 1. 聊天背景 <br>
 * 2. 加号展开后的按钮和动作，如自定义消息 <br>
 * 3. ActionBar右侧按钮。
 * <p>
 * Created by hzchenkang on 2016/12/19.
 */

public class DefalutP2PSessionCustomization extends SessionCustomization {

    public DefalutP2PSessionCustomization() {
        InputPanelCustomization inputPanelCustomization = new InputPanelCustomization() {
            @Override
            public MsgAttachment createStickerAttachment(String category, String item) {
                return null;
            }
        };
        inputPanelCustomization.setShowEmojiInputBar(true);
        inputPanelCustomization.setShowAudioInputBar(true);
        inputPanelCustomization.setWithSticker(true);
        setInputPanelCustomization(inputPanelCustomization);
    }

}
