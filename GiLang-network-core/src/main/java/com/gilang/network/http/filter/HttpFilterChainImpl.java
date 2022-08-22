package com.gilang.network.http.filter;

import com.gilang.common.domian.http.HttpDataRequest;
import com.gilang.common.domian.http.HttpDataResponse;
import lombok.Setter;

/**
 * @author gylang
 * data 2022/8/22
 */
public class HttpFilterChainImpl implements HttpFilterChain {

    private final HttpFilter httpFilter;
    @Setter
    private HttpFilterChain next;

    public HttpFilterChainImpl(HttpFilter httpFilter) {
        this.httpFilter = httpFilter;
    }

    @Override
    public void doFilter(HttpDataRequest<?> request, HttpDataResponse response) {
        httpFilter.doFilter(request, response, next);
    }
}
