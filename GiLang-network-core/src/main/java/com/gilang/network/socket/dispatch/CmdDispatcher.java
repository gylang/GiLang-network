package com.gilang.network.socket.dispatch;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.context.SessionContext;

/**
 * @author gylang
 * data 2022/5/31
 */
public interface CmdDispatcher<T> {

    /**
     * 请求分发
     * @param dataPackage 消息数据包
     */
    void dispatch(T dataPackage, SessionContext sessionContext);
}
