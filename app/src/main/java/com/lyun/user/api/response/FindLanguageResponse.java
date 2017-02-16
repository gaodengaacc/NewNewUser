package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * @author Gordon
 * @since 2017/2/16
 * do()
 */

public class FindLanguageResponse extends BaseResponse {
    private int language_id;
    private String language;

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


}
