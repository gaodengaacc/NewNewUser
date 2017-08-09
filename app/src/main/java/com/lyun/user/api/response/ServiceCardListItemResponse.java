package com.lyun.user.api.response;

import com.lyun.api.response.BaseResponse;

/**
 * @author Gordon
 * @since 2017/7/31
 * do()
 */

public class ServiceCardListItemResponse extends BaseResponse{
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    private String title;
    private String value;
    private int cardId;
    public ServiceCardListItemResponse(String title,String value,int cardId){
        this.cardId = cardId;
        this.title = title;
        this.value = value;
    }
}
