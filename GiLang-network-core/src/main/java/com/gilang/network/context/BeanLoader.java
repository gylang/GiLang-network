package com.gilang.network.context;

import com.gilang.common.context.BeanLoadWrapper;

import java.util.List;

/**
 * @author gylang
 * data 2022/7/6
 */
public interface BeanLoader {

    /**
     * 扫描bean
     *
     * @param serverContext 上下文
     * @return
     */
    List<BeanLoadWrapper<?>> scan(ServerContext serverContext);
}
