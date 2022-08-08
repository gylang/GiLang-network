package com.gilang.network.layer.app.socket;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.context.ServerContext;
import com.gilang.network.context.SocketSessionContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;

/**
 * @author gylang
 * data 2022/6/22
 */
public class SocketDoActionHookHolder implements AfterNetWorkContextInitialized {

    /**
     * 执行action之前调用
     *
     * @param dataPackage    数据包
     * @param socketSessionContext session上下文
     * @param messageAction  当前调用的action
     */
    public  void doActionBefore(SocketDataPackage<?> dataPackage, SocketSessionContext socketSessionContext, MessageAction<?> messageAction) {

    }

    /**
     * 执行action之后调用
     *
     * @param dataPackage    数据包
     * @param socketSessionContext session上下文
     * @param messageAction  当前调用的action
     */
    public  void doActionAfter(SocketDataPackage<?> dataPackage, SocketSessionContext socketSessionContext, MessageAction<?> messageAction) {

    }


    @Override
    public void post(ServerContext serverContext) {
        // todo SocketDoActionHook的责任链如何链接在一起
    }
}
