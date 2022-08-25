package com.gilang.network.socket;

import com.gilang.network.context.SessionContext;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author gylang
 * data 2022/7/17
 */
public interface SocketSessionManager {


    /**
     * 绑定别名
     *
     * @param aliasKey   别名
     * @param sessionKey 会话关键字
     * @return 绑定成功 true
     */
    boolean bindAlias(String aliasKey, String sessionKey);

    /**
     * 通过别名解除别名绑定
     *
     * @param aliasKey 别名
     */
    void unbindAliasByAliasKey(String aliasKey);

    /**
     * 通过会话关键字解除别名绑定
     *
     * @param sessionKey 会话关键字
     */
    void unbindAliasBySessionKey(String sessionKey);

    /**
     * 注册会话
     *
     * @param sessionKey     会话关键字
     * @param sessionContext 会话上下文
     */
    void register(String sessionKey, SessionContext sessionContext);

    /**
     * 移除会话
     *
     * @param sessionKey 会话关键字
     */
    void remove(String sessionKey);

    /**
     * 通过sessionKey获取会话上下文
     *
     * @param sessionKey 会话关键字
     * @return 会话上下文
     */
    SessionContext getSessionBySessionKey(String sessionKey);

    /**
     * 通过别名获取会话上下文
     *
     * @param aliasKey 别名
     * @return 会话上下文
     */
    SessionContext getSessionByAliasKey(String aliasKey);

    /**
     * 查询符合条件的会话
     * @param query 条件
     * @return 会话列表
     */
    List<SessionContext> querySession(Predicate<SessionContext> query);
}
