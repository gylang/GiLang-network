package com.gilang.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gylang
 * data 2022/6/14
 */
@Getter
public class BeanException extends RuntimeException{

    private final String beanName;

    private final String msg;

    public BeanException(String beanName, String msg) {
        super(msg);
        this.beanName = beanName;
        this.msg = msg;
    }
}
