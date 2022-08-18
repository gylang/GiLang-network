package com.gilang.network.http.filter;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;

/**
 * @author gylang
 * data 2022/8/18
 */
public interface HttpFilter {


    /**
     * 过滤器
     *
     * @param request  请求
     * @param response 响应
     * @param chain    下一个过滤器
     */
    void doFilter(HttpDataRequest<?> request, HttpDataResponse response, HttpFilter chain);

}
