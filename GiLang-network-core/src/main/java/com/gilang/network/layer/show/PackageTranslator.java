package com.gilang.network.layer.show;

import java.lang.reflect.Type;

/**
 * 报文翻译
 *
 * @author gylang
 * data 2022/6/14
 */
public interface PackageTranslator {

    /**
     * 将对象翻译成java对象
     *
     * @param bs   字节
     * @param type 类型
     * @return java对象
     */
    Object toObject(byte[] bs, Type type);

    /**
     * 将对象翻译成byte数组
     *
     * @param object java对象
     * @return byte数组
     */
    byte[] toByte(Object object);
}
