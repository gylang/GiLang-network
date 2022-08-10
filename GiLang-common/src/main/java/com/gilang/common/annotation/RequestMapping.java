package com.gilang.common.annotation;

import com.gilang.common.enums.RequestMethod;

import java.lang.annotation.*;

/**
 * 映射请求
 *
 * @author gylang
 * data 2022/8/10
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    /** 请求方法类型 */
    RequestMethod[] method() default {};

    /** 响应类型 */
    String contentType() default "";

    /** 请求地址 */
    String path();
}
