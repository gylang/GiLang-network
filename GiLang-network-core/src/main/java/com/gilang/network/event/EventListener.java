package com.gilang.network.event;

/**
 * @author gylang
 * data 2022/7/17
 */
public interface EventListener<T extends Event> {

    /**
     * 回调监听事件
     *
     * @param t 事件源
     */
    void call(T t);
}
