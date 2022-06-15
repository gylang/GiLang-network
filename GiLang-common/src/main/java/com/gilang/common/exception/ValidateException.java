package com.gilang.common.exception;



/**
 * @author gylang,
 * date 2020/4/14,
 * @version 1.0
 */

public class ValidateException extends BaseException {

    public ValidateException() {
        super(BaseResultCode.PARAMS_ERROR.getCode(), BaseResultCode.PARAMS_ERROR.getMsg());
    }

    public ValidateException(BaseCode baseCode) {
        super(baseCode.getCode(), baseCode.getMsg());
    }
    public ValidateException(String code, String msg) {
        super(code, msg);
    }

    public ValidateException(String msg) {
        super(BaseResultCode.PARAMS_ERROR.getCode(), msg);
    }

    public ValidateException(String message, String code, String msg) {
        super(message, code, msg);
    }

    public ValidateException(String message, Throwable cause, String code, String msg) {
        super(message, cause, code, msg);
    }

    public ValidateException(Throwable cause, String code, String msg) {
        super(cause, code, msg);
    }

    public ValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace, code, msg);
    }
}
