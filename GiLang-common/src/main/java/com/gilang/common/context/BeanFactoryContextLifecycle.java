package com.gilang.common.context;

/**
 * @author gylang
 * data 2022/6/14
 */
public interface BeanFactoryContextLifecycle {

    /**
     * bean注册之前
     *
     * @param beanWrapper bean包装类
     */
    void beanRegisterBefore(BeanWrapper<?> beanWrapper);

    /**
     * bean注册之后
     *
     * @param beanWrapper bean包装类
     */
    void beanRegisterAfter(BeanWrapper<?> beanWrapper);

}
