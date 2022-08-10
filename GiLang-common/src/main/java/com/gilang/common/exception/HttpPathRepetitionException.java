package com.gilang.common.exception;

/**
 * 重复注册同一个uri
 *
 * @author gylang
 * data 2022/8/9
 */
public class HttpPathRepetitionException extends RuntimeException {

    private final String path;
    private final String repetitionPath;

    public HttpPathRepetitionException(String path, String repetitionPath) {
        this.path = path;
        this.repetitionPath = repetitionPath;
    }


    @Override
    public String getMessage() {
        return "http path repetition: [" + repetitionPath + ',' + path + "]";
    }
}
