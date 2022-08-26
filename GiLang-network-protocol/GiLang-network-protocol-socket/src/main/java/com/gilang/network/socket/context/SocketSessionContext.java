package com.gilang.network.socket.context;

import lombok.Data;

/**
 * @author gylang
 * data 2022/6/16
 */
@Data
public abstract class SocketSessionContext {

    /** 会话id */
    private String id;

    /** 授权之后的用户id等 */
    private String aliasId;

    /** 设备类型 */
    private String equipType;

    /** 接入渠道 */
    private String appType;

    /** 额外的属性参数 */
    public abstract <T> T attr(String key);
    /** 额外的属性参数 */
    public abstract void setAttr(String key, Object attr);

    /** 判断是否存在该额外的属性参数 */
    public abstract boolean hasAttr(String key);

    /** 当前连接是否断开 */
    public abstract boolean isRemoved();

    /**
     * 写入消息
     * @param message 写入消息
     */
    public abstract void write(Object message);



}
