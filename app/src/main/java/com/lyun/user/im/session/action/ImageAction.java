package com.lyun.user.im.session.action;

import com.lyun.user.R;
import com.netease.nim.uikit.session.ToolbarCustomization;
import com.netease.nim.uikit.session.actions.PickImageAction;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.File;

/**
 * Created by ZHAOWEIWEI on 2017/2/24.
 */

public class ImageAction extends PickImageAction {

    public ImageAction() {
        super(R.drawable.message_plus_photo_selector, R.string.input_panel_photo, true);
    }
    public ImageAction(ToolbarCustomization toolbarCustomization) {
        super(R.drawable.message_plus_photo_selector, R.string.input_panel_photo, false, toolbarCustomization);
    }
    @Override
    protected void onPicked(File file) {
        IMMessage message = MessageBuilder.createImageMessage(getAccount(), getSessionType(), file, file.getName());
        sendMessage(message);
    }

}
