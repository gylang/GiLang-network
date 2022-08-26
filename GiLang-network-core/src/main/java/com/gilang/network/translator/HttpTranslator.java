package com.gilang.network.translator;

import com.gilang.common.domian.http.HttpDataRequest;

import java.lang.reflect.Type;

/**
 * @author gylang
 * data 2022/8/11
 */
public interface HttpTranslator {


    /**
     * 将对象翻译成byte数组
     *
     * @param object java对象
     * @return byte数组
     */
    byte[] toByte(Object object);

    /**
     * 支持的contentType
     *
     * @return contentType
     */
    String[] supportContentTypes();

    /**
     * 将byte数组翻译成java对象
     *
     * @param bs              字节
     * @param type            类型
     * @param httpDataRequest 请求对象
     */
     Object toObject(byte[] bs, Type type, HttpDataRequest<?> httpDataRequest);
}
