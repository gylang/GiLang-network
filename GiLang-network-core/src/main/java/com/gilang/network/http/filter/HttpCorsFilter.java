package com.gilang.network.http.filter;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;

/**
 * @author gylang
 * data 2022/8/20
 */
public class HttpCorsFilter implements HttpFilter{
    @Override
    public void doFilter(HttpDataRequest<?> request, HttpDataResponse response, HttpFilter chain) {

        // 先默认全部通过
        response.setHeader("Access-Control-Allow-Origin", "*");
    }
}
