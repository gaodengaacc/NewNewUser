package com.lyun.user.im.session.extension;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;

/**
 * Created by zhoujianghua on 2015/7/8.
 */
public class SnapChatAttachment extends FileAttachment {

    private static final String KEY_PATH = "path";
    private static final String KEY_SIZE = "size";
    private static final String KEY_MD5 = "md5";
    private static final String KEY_URL = "url";

    public SnapChatAttachment() {
        super();
    }

    public SnapChatAttachment(JsonObject data) {
        load(data);
    }

    @Override
    public String toJson(boolean send) {
        JsonObject data = new JsonObject();
        try {
            if (!send && !TextUtils.isEmpty(path)) {
                data.addProperty(KEY_PATH, path);
            }

            if (!TextUtils.isEmpty(md5)) {
                data.addProperty(KEY_MD5, md5);
            }

            data.addProperty(KEY_URL, url);
            data.addProperty(KEY_SIZE, size);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CustomAttachParser.packData(CustomAttachmentType.SnapChat, data);
    }

    private void load(JsonObject data) {
        path = data.get(KEY_PATH).getAsString();
        md5 = data.get(KEY_MD5).getAsString();
        url = data.get(KEY_URL).getAsString();
        size = data.has(KEY_SIZE) ? data.get(KEY_SIZE).getAsLong() : 0;
    }
}
