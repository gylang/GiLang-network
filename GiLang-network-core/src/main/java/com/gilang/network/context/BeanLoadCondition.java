package com.gilang.network.context;

import com.gilang.common.context.BeanLoadWrapper;

import java.util.List;

/**
 * @author gylang
 * data 2022/7/6
 */
public interface BeanLoadCondition {

    /**
     * 判断是否进行注册
     *
     * @param serverContext 上下文
     * @param currentBean           当前注册的bean
     * @param allBean           所有需要注册的bean
     * @return true: 可以注册
     */
    boolean judge(ServerContext serverContext, BeanLoadWrapper<?> currentBean, List<BeanLoadWrapper<?>> allBean);
}
