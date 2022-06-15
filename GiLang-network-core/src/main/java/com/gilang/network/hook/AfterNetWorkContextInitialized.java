package com.gilang.network.hook;

import com.gilang.network.context.ServerContext;

/**
 *
 * @author gylang
 * data 2022/6/7
 */
public interface AfterNetWorkContextInitialized {

    void post(ServerContext serverContext);
}
