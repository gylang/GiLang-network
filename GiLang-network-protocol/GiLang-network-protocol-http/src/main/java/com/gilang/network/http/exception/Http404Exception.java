package com.gilang.network.http.exception;

import com.gilang.common.domian.http.HttpDataRequest;
import lombok.Getter;

/**
 * @author gylang
 * data 2022/8/17
 */
public class Http404Exception extends RuntimeException{

    @Getter
    private final HttpDataRequest<?> httpDataRequest;

    public Http404Exception(HttpDataRequest<?> httpDataRequest) {
        this.httpDataRequest = httpDataRequest;
    }

    @Override
    public String getMessage() {
        return "资源不存在:" + httpDataRequest.getUri();
    }
}
