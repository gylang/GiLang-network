package com.gilang.network.layer.show.http;

import com.gilang.common.domian.http.HttpDataRequest;

/**
 * @author gylang
 * data 2022/8/11
 */
public interface HttpTranslator {


    /**
     * 将对象翻译成java对象
     *
     * @param bs              字节
     * @param type            类型
     * @param httpDataRequest 请求体
     */
    <T> void writeBody(byte[] bs, Class<T> type, HttpDataRequest<T> httpDataRequest);

    /**
     * 将对象翻译成byte数组
     *
     * @param object java对象
     * @return byte数组
     */
    byte[] toByte(Object object);

    /**
     * 支持的contentType
     * @return contentType
     */
    String[] supportContentTypes();
}
