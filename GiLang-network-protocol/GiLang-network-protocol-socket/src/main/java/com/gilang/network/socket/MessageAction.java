package com.gilang.network.socket;

import com.gilang.common.domian.socket.SocketDataPackage;
import com.gilang.network.context.SessionContext;

/**
 * 消息体的对象不建议做泛型, 因为处理起来很麻烦, 如果要使用list, 建议在上层加一个包装类
 *
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
