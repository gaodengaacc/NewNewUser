package com.lyun.user.eventbusmessage;

/**
 * @author Gordon
 * @since 2017/7/27
 * do()
 */

public interface EventMessage<T> {
    void setMessage(T o);
    T getMessage();
}
