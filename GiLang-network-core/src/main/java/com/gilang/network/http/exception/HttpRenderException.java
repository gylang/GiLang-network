package com.gilang.network.http.exception;

import lombok.Getter;

/**
 * http 写入渲染时产生的异常
 * @author 23516
 * @since 2022/8/18
 */

public class HttpRenderException extends RuntimeException{

    @Getter
    private final Exception exception;

    public HttpRenderException(Exception exception) {
        this.exception = exception;
    }
}
