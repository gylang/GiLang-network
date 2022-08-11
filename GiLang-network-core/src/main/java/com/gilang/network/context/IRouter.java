package com.gilang.network.context;

/**
 * @author gylang
 * data 2022/5/31
 */
public interface IRouter<T,C> {

    /**
     * 请求分发
     * @param dataPackage 消息数据包
     */
    void route(T dataPackage, C socketSessionContext);
}
