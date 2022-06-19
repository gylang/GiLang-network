package com.gilang.network.socket.router;

import com.gilang.network.context.SessionContext;

/**
 * @author gylang
 * data 2022/5/31
 */
public interface SocketRouter<T> {

    /**
     * 请求分发
     * @param dataPackage 消息数据包
     */
    void route(T dataPackage, SessionContext sessionContext);
}
