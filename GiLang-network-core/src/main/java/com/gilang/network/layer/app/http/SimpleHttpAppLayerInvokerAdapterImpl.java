package com.gilang.network.layer.app.http;

import com.gilang.common.domian.HttpDataPackage;
import com.gilang.network.context.SocketSessionContext;

import java.lang.reflect.Type;

/**
 * @author gylang
 * data 2022/8/8
 */
public class SimpleHttpAppLayerInvokerAdapterImpl implements HttpAppLayerInvokerAdapter<HttpDataPackage<?>> {




    @Override
    public void route(HttpDataPackage<?> dataPackage, SocketSessionContext socketSessionContext) {

    }

    @Override
    public Type resolveInvokeParamType(HttpDataPackage<?> data) {
        return null;
    }

    @Override
    public Object toObject(byte protocol, byte[] bs, Type type) {
        return null;
    }

    @Override
    public byte[] toByte(byte protocol, Object object) {
        return new byte[0];
    }
}
