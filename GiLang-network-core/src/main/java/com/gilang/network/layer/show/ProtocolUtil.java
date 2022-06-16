package com.gilang.network.layer.show;

/**
 * @author gylang
 * data 2022/6/16
 */
public class ProtocolUtil {


    public static byte readTranslator(byte bit1) {

        return (byte) (bit1 & 0b11110000);
    }

    public static byte readInLabel(byte bit1) {
        return (byte) (bit1 & 0b00001111);
    }

    public static byte readQos(byte bit3) {
        return (byte) (bit3 & 0b11110000);
    }

    public static byte readAck(byte bit3) {
        return (byte) (bit3 & 0b00001111);
    }
}
