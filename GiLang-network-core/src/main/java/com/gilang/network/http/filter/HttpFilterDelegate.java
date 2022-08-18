package com.gilang.network.http.filter;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import com.gilang.network.context.ServerContext;
import com.gilang.network.hook.AfterNetWorkContextInitialized;

/**
 * @author gylang
 * data 2022/8/18
 */
public class HttpFilterDelegate implements AfterNetWorkContextInitialized {


    /**
     * 过滤器
     *
     * @param request  请求
     * @param response 响应
     */
    void doFilter(HttpDataRequest<?> request, HttpDataResponse response) {


    }

    @Override
    public void post(ServerContext serverContext) {

    }
}
