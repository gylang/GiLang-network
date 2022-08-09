package com.gilang.network.layer.app.http;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.network.context.SocketSessionContext;

import java.lang.reflect.Type;

/**
 * @author gylang
 * data 2022/8/8
 */
public class SimpleHttpAppLayerInvokerAdapterImpl implements HttpAppLayerInvokerAdapter<HttpDataRequest<?>> {




    @Override
    public void route(HttpDataRequest<?> dataPackage, SocketSessionContext socketSessionContext) {

    }

    @Override
    public Type resolveInvokeParamType(HttpDataRequest<?> data) {
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
