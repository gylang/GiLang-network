package com.gilang.common.domian;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * |4bit translatorType|4bit inLabel|8bit cmd|4bit qos|4bit ack|
 * |64bit 64bit                                                |
 * |16bit contentLength |     appContent  ....                 |
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

    /** 8bit 指令类型 因通过二进制方式声明 [0b11111111] */
    private byte cmd;

    /** 4bit 质量标识 */
    private byte qos;

    /** 4bit ack标识 */
    private byte ack;

    public SocketDataPackage(T object) {
        setActBody(object);
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
        setActBody(actBody);
        return this;
    }
}
