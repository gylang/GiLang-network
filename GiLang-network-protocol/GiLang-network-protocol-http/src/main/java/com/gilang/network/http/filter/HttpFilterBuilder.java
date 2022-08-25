package com.gilang.network.http.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gylang
 * data 2022/8/18
 */
public class HttpFilterBuilder {

    private Map<Integer, Class<?>> filterOrder = new HashMap<>();

    public void addBefore(Class<?> before, Class<?> after) {

    }
    public void addAfter(Class<?> after , Class<?> before) {

    }
}
