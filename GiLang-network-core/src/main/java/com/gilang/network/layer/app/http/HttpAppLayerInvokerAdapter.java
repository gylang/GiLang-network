package com.gilang.network.layer.app.http;

import com.gilang.common.domian.HttpDataPackage;
import com.gilang.network.socket.router.SocketRouter;

import java.lang.reflect.Type;

/**
 * @author gylang
 * data 2022/6/15
 */
public interface HttpAppLayerInvokerAdapter<T> extends SocketRouter<HttpDataPackage<?>> {

    /**
     * 下层传入解析调用的参数类型
     *
     * @param data 下层参数用户解析的数据
     * @return 参数类型
     */
    Type resolveInvokeParamType(T data);

    /**
     * 将对象翻译成java对象
     *
     * @param bs   字节
     * @param type 类型
     * @return java对象
     */
    Object toObject(byte protocol, byte[] bs, Type type);

    /**
     * 将对象翻译成byte数组
     *
     * @param object java对象
     * @return byte数组
     */
    byte[] toByte(byte protocol, Object object);
}