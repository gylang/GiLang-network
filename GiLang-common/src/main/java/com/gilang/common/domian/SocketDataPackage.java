package com.gilang.common.domian;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * |4bit translatorType|4bit inLabel|4bit qos|4bit ack|8bit cmd|
 * |64bit msgId                                                |
 * |16bit payloadLength |     appContent  ....                 |
 * |          ........       appContent   ....                 |
 *
 * @author gylang
 * data 2022/6/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SocketDataPackage<T> extends DataPackage<T> {

    /** 4bit 翻译方式类型 (用户展示层数据转换使用) */
    private byte translatorType;

    /** 4bit 用于后续系统内部处理标识 (预留扩展: 系统流转,分布式分发) */
    private byte inLabel;

    /** 4bit 质量标识 */
    private byte qos;

    /** 4bit ack标识 */
    private byte ack;

    /** 8bit 指令类型 因通过二进制方式声明 [0b11111111] */
    private byte cmd;

    /**
     * 单次发送的消息类型
     *
     * @return 单次发送
     */
    public boolean isAwaySend() {
        return qos == 0;
    }

    /**
     * 确保有一次成功的消息类型
     *
     * @return 确保有一次成功
     */
    public boolean isOneSend() {
        return qos == 1;
    }

    /**
     * 确保仅有一次成功的消息类型
     *
     * @return 确保仅有一次成功
     */
    public boolean isOnlyOneSend() {
        return qos == 1;
    }

    public SocketDataPackage(T object) {
        setPayload(object);
    }

    public SocketDataPackage() {
    }


    public SocketDataPackage<T> translatorType(byte translatorType) {
        this.translatorType = translatorType;
        return this;
    }

    public SocketDataPackage<T> inLabel(byte inLabel) {
        this.inLabel = inLabel;
        return this;
    }

    public SocketDataPackage<T> cmd(byte cmd) {
        this.cmd = cmd;
        return this;
    }

    public SocketDataPackage<T> qos(byte qos) {
        this.qos = qos;
        return this;
    }

    public SocketDataPackage<T> ack(byte ack) {
        this.ack = ack;
        return this;
    }

    public SocketDataPackage<T> msgId(long msgId) {
        setMsgId(msgId);
        return this;
    }

    public SocketDataPackage<T> actBody(T actBody) {
        setPayload(actBody);
        return this;
    }


}
