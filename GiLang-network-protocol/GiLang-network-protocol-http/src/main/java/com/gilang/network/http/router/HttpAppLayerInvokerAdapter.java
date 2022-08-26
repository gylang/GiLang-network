package com.gilang.network.http.router;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.common.domian.http.HttpServiceWrapper;

import java.lang.reflect.Type;

/**
 * @author gylang
 * data 2022/6/15
 */
public interface HttpAppLayerInvokerAdapter extends HttpRouter<HttpDataRequest<?>> {

    /**
     * 下层传入解析调用的参数类型
     *
     * @param request 请求对象
     * @return 参数类型
     */
    public HttpServiceWrapper searchServiceWrapper(HttpDataRequest<?> request);

    /**
     * 将对象翻译成java对象
     *
     * @param bs   字节
     * @param type 类型
     * @param dataRequest 请求对象
     * @return java对象
     */
    Object toObject(String contentType, byte[] bs, Type type, HttpDataRequest<?> dataRequest);

    /**
     * 将对象翻译成byte数组
     *
     * @param response 响应对象
     * @return byte数组
     */
    byte[] toByte(HttpDataResponse response);
}
