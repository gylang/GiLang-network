package com.gilang.common.context;

import lombok.Data;

import java.util.function.Supplier;

/**
 * @author gylang
 * data 2022/6/14
 */
@Data
public class BeanLoadWrapper<T> {

    /** bean的名称 */
    private Class<T> clazz;

    /** bean */
    private Supplier<T> beanInitFunc;

    public BeanLoadWrapper(Class<T> clazz, Supplier<T> beanInitFunc) {
        this.clazz = clazz;
        this.beanInitFunc = beanInitFunc;
    }
}
