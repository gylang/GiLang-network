package com.gilang.network.exception;

import com.gilang.common.exception.BaseException;

/**
 * @author gylang
 * data 2022/6/15
 */
public class MultiCommandException extends RuntimeException {

    public MultiCommandException(String msg) {
        super( msg);
    }
}
