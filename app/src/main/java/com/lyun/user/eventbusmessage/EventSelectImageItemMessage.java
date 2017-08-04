package com.lyun.user.eventbusmessage;

/**
 * @author Gordon
 * @since 2017/8/3
 * do()
 */

public class EventSelectImageItemMessage implements EventMessage<Integer> {
    private Integer itemId;

    @Override
    public void setMessage(Integer o) {
        this.itemId = o;
    }

    @Override
    public Integer getMessage() {
        return itemId;
    }

    public EventSelectImageItemMessage(Integer itemId) {
        this.itemId = itemId;
    }
}
