package com.gilang.network.layer.session;

import cn.hutool.core.map.BiMap;
import com.gilang.network.context.SocketSessionContext;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author gylang
 * data 2022/7/17
 */
public class SocketSessionManagerImpl implements SocketSessionManager {

    /** 别名绑定 k:aliasKey v:sessionLey */
    private final BiMap<String, String> aliasMapping = new BiMap<>(new ConcurrentHashMap<>());
    /** 会话缓存 */
    private final Map<String, SocketSessionContext> sessionContextMap = new ConcurrentHashMap<>();


    @Override
    public boolean bindAlias(String aliasKey, String sessionKey) {
        if (sessionContextMap.containsKey(sessionKey)) {
            aliasMapping.put(aliasKey, sessionKey);
            return true;
        }
        return false;
    }

    @Override
    public void unbindAliasByAliasKey(String aliasKey) {
        aliasMapping.remove(aliasKey);
    }


    @Override
    public void unbindAliasBySessionKey(String sessionKey) {
        aliasMapping.getRaw().remove(sessionKey);
    }


    @Override
    public void register(String sessionKey, SocketSessionContext socketSessionContext) {
        sessionContextMap.put(sessionKey, socketSessionContext);
    }


    @Override
    public void remove(String sessionKey) {
        sessionContextMap.remove(sessionKey);
    }

    @Override
    public SocketSessionContext getSessionBySessionKey(String sessionKey) {
        return sessionContextMap.get(sessionKey);
    }

    @Override
    public SocketSessionContext getSessionByAliasKey(String aliasKey) {
        String sessionKey = aliasMapping.get(aliasKey);
        if (null != sessionKey) {
            return sessionContextMap.get(sessionKey);
        }
        return null;
    }

    @Override
    public List<SocketSessionContext> querySession(Predicate<SocketSessionContext> query) {
        return sessionContextMap.values().stream().filter(query).collect(Collectors.toList());
    }

}
