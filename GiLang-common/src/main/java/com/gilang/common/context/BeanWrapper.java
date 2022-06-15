package com.gilang.common.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author gylang
 * data 2022/6/14
 */
@Data
public class BeanWrapper<T> {

    /** bean的名称 */
    private String name;

    /** bean */
    private T bean;

    /** bean的类型 */
    private List<Class<?>> type;

    public BeanWrapper(T bean) {
        this.bean = bean;
    }

    public BeanWrapper(String name, T bean, List<Class<?>> type) {
        this.name = name;
        this.bean = bean;
        this.type = type;
    }

}
