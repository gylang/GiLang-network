package com.gilang.network.http.handler;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;

/**
 * 捕获http请求的异常, 并进行处理
 *
 * @author gylang
 * data 2022/8/16
 */
public interface HttpExceptionHandler<E extends Exception> {

    /**
     * 异常处理
     *
     * @param request   请求对象
     * @param response  响应对象
     * @param exception 异常信息
     */
    void handler(HttpDataRequest<Object> request, HttpDataResponse response, E exception);

}
