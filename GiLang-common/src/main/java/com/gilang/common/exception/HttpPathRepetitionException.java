package com.gilang.common.exception;

/**
 * 重复注册同一个uri
 * @author gylang
 * data 2022/8/9
 */
public class HttpPathRepetitionException extends RuntimeException {

    private String path;

    public HttpPathRepetitionException(String path) {
        this.path = path;
    }


    @Override
    public String getMessage() {
        return "http path repetition: [" + path + "]";
    }
}
