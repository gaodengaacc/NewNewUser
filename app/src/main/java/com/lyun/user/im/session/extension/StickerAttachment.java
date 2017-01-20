package com.lyun.user.im.session.extension;

import com.google.gson.JsonObject;
import com.netease.nim.uikit.common.util.file.FileUtil;

/**
 * Created by zhoujianghua on 2015/7/8.
 */
public class StickerAttachment extends CustomAttachment {

    private final String KEY_CATALOG = "catalog";
    private final String KEY_CHARTLET = "chartlet";

    private String catalog;
    private String chartlet;

    public StickerAttachment() {
        super(CustomAttachmentType.Sticker);
    }

    public StickerAttachment(String catalog, String emotion) {
        this();
        this.catalog = catalog;
        this.chartlet = FileUtil.getFileNameNoEx(emotion);
    }

    @Override
    protected void parseData(JsonObject data) {
        this.catalog = data.get(KEY_CATALOG).getAsString();
        this.chartlet = data.get(KEY_CHARTLET).getAsString();
    }

    @Override
    protected JsonObject packData() {
        JsonObject data = new JsonObject();
        data.addProperty(KEY_CATALOG, catalog);
        data.addProperty(KEY_CHARTLET, chartlet);
        return data;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getChartlet() {
        return chartlet;
    }
}
