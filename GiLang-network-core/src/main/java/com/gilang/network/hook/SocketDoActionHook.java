package com.gilang.network.hook;

import com.gilang.common.domian.socket.SocketDataPackage;
import com.gilang.network.context.SocketSessionContext;
import com.gilang.network.layer.app.socket.MessageAction;

/**
 * 执行action方法的钩子函数
 *
 * @author gylang
 * data 2022/6/22
 */
public abstract class SocketDoActionHook {

    private SocketDoActionHook socketDoActionHook;

    /**
     * 执行action之前调用
     *
     * @param dataPackage    数据包
     * @param socketSessionContext session上下文
     * @param messageAction  当前调用的action
     */
    public abstract void doActionBefore(SocketDataPackage<?> dataPackage, SocketSessionContext socketSessionContext, MessageAction<?> messageAction);

    /**
     * 执行action之后调用
     *
     * @param dataPackage    数据包
     * @param socketSessionContext session上下文
     * @param messageAction  当前调用的action
     */
    public abstract void doActionAfter(SocketDataPackage<?> dataPackage, SocketSessionContext socketSessionContext, MessageAction<?> messageAction);

    /**
     * 执行下一个钩子函数
     *
     * @param dataPackage    数据包
     * @param socketSessionContext session上下文
     * @param messageAction  当前调用的action
     */
    public void nextDoActionBefore(SocketDataPackage<?> dataPackage, SocketSessionContext socketSessionContext, MessageAction<?> messageAction) {
        if (null != socketDoActionHook) {
            socketDoActionHook.doActionBefore(dataPackage, socketSessionContext, messageAction);
        }
    }

    /**
     * 执行下一个钩子函数
     *
     * @param dataPackage    数据包
     * @param socketSessionContext session上下文
     * @param messageAction  当前调用的action
     */
    public void nextDoActionAfter(SocketDataPackage<?> dataPackage, SocketSessionContext socketSessionContext, MessageAction<?> messageAction) {
        if (null != socketDoActionHook) {
            socketDoActionHook.doActionAfter(dataPackage, socketSessionContext, messageAction);
        }
    }

    public void addNext(SocketDoActionHook socketDoActionHook) {
        this.socketDoActionHook = socketDoActionHook;
    }
}
