package com.lyun.user.service;

import java.io.Serializable;

/**
 * Created by ZHAOWEIWEI on 2017/2/28.
 */

public class TranslationOrder implements Serializable {

    private String orderId;
    private long startTime;
    private long servicedTime;
    private long endTime;
    private String userId;
    private String translatorId;

    public static final String ORDER_ID = "orderId";
    public static final String START_TIME = "startTime";
    public static final String SERVICED_TIME = "servicedTime";
    public static final String END_TIME = "endTime";
    public static final String USER_ID = "userId";
    public static final String TRANSLATOR_ID = "translatorId";

    public TranslationOrder(String orderId, long startTime, String userId, String translatorId) {
        this.orderId = orderId;
        this.startTime = startTime;
        this.userId = userId;
        this.translatorId = translatorId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getServicedTime() {
        return servicedTime;
    }

    public void setServicedTime(long servicedTime) {
        this.servicedTime = servicedTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTranslatorId() {
        return translatorId;
    }

    public void setTranslatorId(String translatorId) {
        this.translatorId = translatorId;
    }
}
