package com.gilang.network.context;

/**
 * @author gylang
 * data 2022/7/6
 */
public interface BeanLoader {

    /**
     * 扫描bean
     *
     * @param serverContext 上下文
     */
    void scan(ServerContext serverContext);
}
