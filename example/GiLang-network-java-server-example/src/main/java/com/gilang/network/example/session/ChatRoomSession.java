package com.gilang.network.example.session;

import com.gilang.network.example.domain.UserInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gylang
 * data 2022/7/17
 */
public class ChatRoomSession {

    private Map<String, Map<Long, UserInfo>> chatRoomUserPool = new ConcurrentHashMap<>();

    public Map<Long, UserInfo> getRoomUsers(String roomKey) {
        return chatRoomUserPool.get(roomKey);
    }

    public UserInfo getRoomUser(String roomKey, Long userId) {
        Map<Long, UserInfo> longUserInfoMap = chatRoomUserPool.get(roomKey);
        return null != longUserInfoMap ? longUserInfoMap.get(userId) : null;
    }
}
