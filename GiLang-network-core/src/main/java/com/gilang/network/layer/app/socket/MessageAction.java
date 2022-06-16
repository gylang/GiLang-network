package com.gilang.network.layer.app.socket;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.context.SessionContext;

/**
 * @author gylang
 * data 2022/6/15
 */
public interface MessageAction<T> {

    /**
     * socket业务请求
     *
     * @param dataPackage 请求数据包
     */
    void doAction(SocketDataPackage<T> dataPackage, SessionContext sessionContext);
}
