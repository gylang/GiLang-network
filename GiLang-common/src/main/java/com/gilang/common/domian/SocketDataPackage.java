package com.gilang.common.domian;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gylang
 * data 2022/6/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SocketDataPackage<T> extends DataPackage<T>{

    /** 4bit 协议号 (用户展示层数据转换使用) */
    private byte protocol;

    /** 4bit 用于后续系统内部处理标识 (预留扩展: 系统流转,分布式分发) */
    private byte inLabel;

    /** 8bit 指令类型 因通过二进制方式声明 [0b11111111] */
    private byte cmd;

    /** 4bit 质量标识 */
    private byte qos;

    /** 4bit ack标识 */
    private byte ack;
}
