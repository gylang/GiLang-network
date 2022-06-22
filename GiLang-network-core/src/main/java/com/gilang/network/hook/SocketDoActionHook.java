package com.gilang.network.hook;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.context.SessionContext;
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
     * @param sessionContext session上下文
     * @param messageAction  当前调用的action
     */
    public abstract void doActionBefore(SocketDataPackage<?> dataPackage, SessionContext sessionContext, MessageAction<?> messageAction);

    /**
     * 执行action之后调用
     *
     * @param dataPackage    数据包
     * @param sessionContext session上下文
     * @param messageAction  当前调用的action
     */
    public abstract void doActionAfter(SocketDataPackage<?> dataPackage, SessionContext sessionContext, MessageAction<?> messageAction);

    /**
     * 执行下一个钩子函数
     *
     * @param dataPackage    数据包
     * @param sessionContext session上下文
     * @param messageAction  当前调用的action
     */
    public void nextDoActionBefore(SocketDataPackage<?> dataPackage, SessionContext sessionContext, MessageAction<?> messageAction) {
        if (null != socketDoActionHook) {
            socketDoActionHook.doActionBefore(dataPackage, sessionContext, messageAction);
        }
    }

    /**
     * 执行下一个钩子函数
     *
     * @param dataPackage    数据包
     * @param sessionContext session上下文
     * @param messageAction  当前调用的action
     */
    public void nextDoActionAfter(SocketDataPackage<?> dataPackage, SessionContext sessionContext, MessageAction<?> messageAction) {
        if (null != socketDoActionHook) {
            socketDoActionHook.doActionAfter(dataPackage, sessionContext, messageAction);
        }
    }

    public void addNext(SocketDoActionHook socketDoActionHook) {
        this.socketDoActionHook = socketDoActionHook;
    }
}
