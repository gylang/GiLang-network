package com.gilang.common.domian.http;

/**
 * @author gylang
 * data 2022/8/9
 */
public interface HttpInvokeHelper {

    /**
     * 调用http请求
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param <T>      参数体类型
     * @return 响应结果
     */
    <T> Object doAction(HttpDataRequest<T> request, HttpDataResponse response);
}
