package com.gilang.network;

import com.gilang.network.context.ServerContext;

/**
 * 服务启动器
 *
 * @author gylang
 * data 2022/6/30
 */
public interface ServerRunner {

    /**
     * 启动服务
     *
     * @param serverContext 服务上下文
     */
    void start(ServerContext serverContext) throws Exception;

    /**
     * 关闭服务
     */
    void close(ServerContext serverContext)throws Exception;
}
