package com.lyun.user.im.session.extension;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by zhoujianghua on 2015/4/10.
 */
public class DefaultCustomAttachment extends CustomAttachment {

    private String content;

    public DefaultCustomAttachment() {
        super(0);
    }

    @Override
    protected void parseData(JsonObject data) {
        content = data.toString();
    }

    @Override
    protected JsonObject packData() {
        JsonObject data = null;
        try {
            data = new JsonParser().parse(content).getAsJsonObject();
        } catch (Exception e) {

        }
        return data;
    }

    public String getContent() {
        return content;
    }
}
