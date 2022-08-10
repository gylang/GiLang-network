package com.gilang.network.layer.app.http;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.common.domian.socket.SocketDataPackage;
import com.gilang.network.context.SocketSessionContext;

/**
 * 消息体的对象不建议做泛型, 因为处理起来很麻烦, 如果要使用list, 建议在上层加一个包装类
 *
 * @author gylang
 * data 2022/6/15
 */
public interface HttpAction<T> {

    /**
     * http业务请求
     *
     * @param dataPackage 请求数据包
     */
    void doAction(HttpDataRequest<T> dataPackage, HttpDataResponse response);
}
