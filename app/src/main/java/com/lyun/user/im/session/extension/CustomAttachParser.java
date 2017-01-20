package com.lyun.user.im.session.extension;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;

/**
 * Created by zhoujianghua on 2015/4/9.
 */
public class CustomAttachParser implements MsgAttachmentParser {

    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";

    @Override
    public MsgAttachment parse(String json) {
        CustomAttachment attachment = null;
        try {
            JsonObject object = new JsonParser().parse(json).getAsJsonObject();
            int type = object.get(KEY_TYPE).getAsInt();
            JsonObject data = object.get(KEY_DATA).getAsJsonObject();
            switch (type) {
                case CustomAttachmentType.Guess:
                    attachment = new GuessAttachment();
                    break;
                case CustomAttachmentType.SnapChat:
                    return new SnapChatAttachment(data);
                case CustomAttachmentType.Sticker:
                    attachment = new StickerAttachment();
                    break;
//                case CustomAttachmentType.RTS:
//                    attachment = new RTSAttachment();
//                    break;
                default:
                    attachment = new DefaultCustomAttachment();
                    break;
            }

            if (attachment != null) {
                attachment.fromJson(data);
            }
        } catch (Exception e) {

        }

        return attachment;
    }

    public static String packData(int type, JsonObject data) {
        JsonObject object = new JsonObject();
        object.addProperty(KEY_TYPE, type);
        if (data != null) {
            object.add(KEY_DATA, data);
        }

        return object.toString();
    }
}
