package com.gilang.network.layer.app;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.layer.show.PackageTranslator;
import com.gilang.network.socket.dispatch.CmdDispatcher;

import java.lang.reflect.Type;

/**
 * @author gylang
 * data 2022/6/15
 */
public interface AppLayerInvokerAdapter<T> extends PackageTranslator, CmdDispatcher<SocketDataPackage<?>> {

    /**
     * 下层传入解析调用的参数类型
     *
     * @param data 下层参数用户解析的数据
     * @return 参数类型
     */
    Type resolveInvokeParamType(T data);

}
