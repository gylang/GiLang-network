package com.gilang.network.example.session;

import com.gilang.network.example.domain.payload.req.LoginReq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gylang
 * data 2022/7/17
 */
public class ChatRoomSession {

    private Map<String, Map<Long, LoginReq>> chatRoomUserPool = new ConcurrentHashMap<>();

    public Map<Long, LoginReq> getRoomUsers(String roomKey) {
        return chatRoomUserPool.get(roomKey);
    }

    public LoginReq getRoomUser(String roomKey, Long userId) {
        Map<Long, LoginReq> longUserInfoMap = chatRoomUserPool.get(roomKey);
        return null != longUserInfoMap ? longUserInfoMap.get(userId) : null;
    }
}
