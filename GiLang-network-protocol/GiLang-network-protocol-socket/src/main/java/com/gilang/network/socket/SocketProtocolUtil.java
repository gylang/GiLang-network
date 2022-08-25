package com.gilang.network.socket;

import cn.hutool.core.util.ByteUtil;
import com.gilang.common.domian.socket.SocketDataPackage;

/**
 * @author gylang
 * data 2022/6/16
 */
public class SocketProtocolUtil {

    /**
     * 协议头部解析payload方式
     *
     * @param bit1 第一个bit
     * @return 解析payload方式类型编码
     */
    public static byte readTranslator(byte bit1) {

        return (byte) (bit1 & 0b11110000);
    }

    /**
     * 协议头部 内部处理标识
     *
     * @param bit1 第一个bit
     * @return 内部处理标识
     */
    public static byte readInLabel(byte bit1) {
        return (byte) (bit1 & 0b00001111);
    }

    /**
     * 协议头部 qos标识
     *
     * @param bit2 第2个bit
     * @return qos标识
     */
    public static byte readQos(byte bit2) {
        return (byte) (bit2 & 0b11110000);
    }

    /**
     * 协议头部 ack标识
     *
     * @param bit2 第2个bit
     * @return ack标识
     */
    public static byte readAck(byte bit2) {
        return (byte) (bit2 & 0b00001111);
    }
    /**
     * 协议头部 ack标识
     *
     * @param socketDataPackage 第2个bit
     * @return ack标识
     */
    public static byte[] writeSocketDataPackage(SocketDataPackage<?> socketDataPackage, byte[] payload) {
        // 0.5byte tt + 0.5byte il + 0.5byte qos + 0.5byte ack + 1byte cmd + 4byte msgId + 2byte payloadLength + ?byte payload
        byte[] bytes = new byte[9 + payload.length];
        // 写入头
        byte bit1 = (byte) ((socketDataPackage.getTranslatorType() & 0b11110000) | (socketDataPackage.getInLabel() & 0b11110000));
        byte bit2 = (byte) ((socketDataPackage.getQos() & 0b11110000) | (socketDataPackage.getAck() & 0b11110000));
        bytes[0] = bit1;
        bytes[1] = bit2;
        bytes[2] = socketDataPackage.getCmd();
        byte[] msgId = ByteUtil.longToBytes(socketDataPackage.getMsgId());
        bytes[3] = msgId[0];
        bytes[4] = msgId[1];
        bytes[5] = msgId[2];
        bytes[6] = msgId[3];
        byte[] length = ByteUtil.shortToBytes((short) payload.length);
        bytes[7] = length[0];
        bytes[8] = length[1];
        return bytes;
    }
}
