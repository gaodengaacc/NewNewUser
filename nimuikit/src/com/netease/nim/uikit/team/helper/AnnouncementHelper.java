package com.netease.nim.uikit.team.helper;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.team.model.Announcement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by huangjun on 2015/3/24.
 */
public class AnnouncementHelper {
    public final static String JSON_KEY_CREATOR = "creator";
    public final static String JSON_KEY_TITLE = "title";
    public final static String JSON_KEY_TIME = "time";
    public final static String JSON_KEY_CONTENT = "content";
    public final static String JSON_KEY_ID = "id";

    public static String makeAnnounceJson(String announce, String title, String content) {
        JsonArray jsonArray = null;
        try {
            jsonArray = new JsonParser().parse(announce).getAsJsonArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (jsonArray == null) {
            jsonArray = new JsonArray();
        }

        JsonObject json = new JsonObject();
        json.addProperty(JSON_KEY_ID, UUID.randomUUID().toString());
        json.addProperty(JSON_KEY_CREATOR, getCreatorName());
        json.addProperty(JSON_KEY_TITLE, title);
        json.addProperty(JSON_KEY_CONTENT, content);
        json.addProperty(JSON_KEY_TIME, (System.currentTimeMillis() / 1000)); // 与ios和pc兼容
        jsonArray.add(json);
        return jsonArray.toString();
    }

    public static List<Announcement> getAnnouncements(String teamId, String announce, int limit) {
        if (TextUtils.isEmpty(announce)) {
            return null;
        }

        List<Announcement> announcements = new ArrayList<>();
        int count = 0;
        JsonArray jsonArray = new JsonParser().parse(announce).getAsJsonArray();
        for (int i = jsonArray.size() - 1; i >= 0; i--) {
            JsonObject json = jsonArray.get(i).getAsJsonObject();
            String id = json.get(JSON_KEY_ID).getAsString();
            String creator = json.get(JSON_KEY_CREATOR).getAsString();
            String title = json.get(JSON_KEY_TITLE).getAsString();
            long time = json.get(JSON_KEY_TIME).getAsLong();
            String content = json.get(JSON_KEY_CONTENT).getAsString();

            announcements.add(new Announcement(id, teamId, creator, title, time, content));

            if (++count >= limit) {
                break;
            }
        }
        return announcements;
    }

    public static Announcement getLastAnnouncement(String teamId, String announcement) {
        List<Announcement> list = getAnnouncements(teamId, announcement, 1);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    private static String getCreatorName() {
        return NimUIKit.getAccount();
    }
}
