package com.gilang.common.annotation;

import com.gilang.common.enums.ServerTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务类型, http,socket, websocket
 *
 * @author gylang
 * data 2022/6/28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExceptionType {

    Class<? extends Exception> value();
}
