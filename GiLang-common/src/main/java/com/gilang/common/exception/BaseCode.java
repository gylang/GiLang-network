package com.gilang.common.exception;

/**
 * @author gylang
 * @date 2021/1/2
 */
public interface BaseCode {

    /**
     * 错误码
     *
     * @return 错误码
     */
    String getCode();

    /**
     * 错误信息
     *
     * @return 错误信息
     */
    String getMsg();
}
