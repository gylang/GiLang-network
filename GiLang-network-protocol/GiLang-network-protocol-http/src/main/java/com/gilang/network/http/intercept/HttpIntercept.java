package com.gilang.network.http.intercept;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.common.domian.http.HttpServiceWrapper;

/**
 * @author gylang
 * data 2022/8/16
 */
public interface HttpIntercept {

    /**
     * 执行请求之前, 判断是否通过拦截器, true为通过
     *
     * @param request        请求对象
     * @param response       响应对象
     * @param serviceWrapper 服务调处理类相关
     * @return true: 通过拦截器
     * @throws Exception 抛出的异常信息
     */
    default boolean preHandle(HttpDataRequest<?> request, HttpDataResponse response, HttpServiceWrapper serviceWrapper)
            throws Exception {

        return true;
    }

    /**
     * 执行服务处理之后进行回调通知
     *
     * @param request        请求对象
     * @param response       响应对象
     * @param serviceWrapper 服务调处理类相关
     * @throws Exception 抛出的异常信息
     */
    default void postHandle(HttpDataRequest<?> request, HttpDataResponse response, HttpServiceWrapper serviceWrapper) throws Exception {
    }

    /**
     * 请求完结之后回调通知
     *
     * @param request        请求对象
     * @param response       响应对象
     * @param serviceWrapper 服务调处理类相关
     * @throws Exception 抛出的异常信息
     */
    default void afterCompletion(HttpDataRequest<?> request, HttpDataResponse response, HttpServiceWrapper serviceWrapper, Exception ex) throws Exception {
    }

    /**
     * 拦截的路径
     */
    String[] interceptPath();

}
