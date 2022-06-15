package com.gilang.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gylang,
 * date 2020/4/14,
 * @version 1.0
 */
@Setter
@Getter
public class BaseException extends RuntimeException {
    private String code;
    private String msg;


    public BaseException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(String message, String code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(String message, Throwable cause, String code, String msg) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(Throwable cause, String code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.msg = msg;
    }

    public BaseException ofCode(String code) {
        this.code = code;
        return this;
    }

    public BaseException ofMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public BaseException ofError(BaseCode baseCode) {
        this.code = baseCode.getCode();
        this.msg = baseCode.getMsg();
        return this;
    }
}
