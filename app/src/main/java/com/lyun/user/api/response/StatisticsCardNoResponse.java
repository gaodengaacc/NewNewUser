package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * Created by 郑成裕 on 2017/2/23.
 */

public class StatisticsCardNoResponse extends BaseResponse {
    private String surplusTime;
    private String useTime;
    private String callFrequency;
    private String languages;

    public String getSurplusTime() {
        return surplusTime;
    }

    public void setSurplusTime(String surplusTime) {
        this.surplusTime = surplusTime;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getCallFrequency() {
        return callFrequency;
    }

    public void setCallFrequency(String callFrequency) {
        this.callFrequency = callFrequency;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
