package com.lyun.user.eventbusmessage.address;

import com.lyun.user.api.response.AddressItemResponse;
import com.lyun.user.eventbusmessage.EventMessage;

/**
 * @author Gordon
 * @since 2017/8/22
 * do()
 */

public class EventAddressSelectMessage implements EventMessage<Integer> {
    private Integer position;

    public int getAction() {
        return action;
    }

    private int action; //0：设置默认地址 1：编辑 2：删除 3:更新

    public AddressItemResponse getResponse() {
        return response;
    }

    public void setResponse(AddressItemResponse response) {
        this.response = response;
    }

    private AddressItemResponse response;

    @Override
    public void setMessage(Integer o) {
        this.position = o;
    }

    @Override
    public Integer getMessage() {
        return position;
    }

    public EventAddressSelectMessage(Integer position, int action) {
        this.position = position;
        this.action = action;
    }
}
