package com.gilang.network.http.filter;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;

/**
 * @author gylang
 * data 2022/8/18
 */
public interface HttpFilterChain {


    /**
     * 过滤器
     *
     * @param request  请求
     * @param response 响应
     */
    void doFilter(HttpDataRequest<?> request, HttpDataResponse response);

    void setNext(HttpFilterChain next);
}
