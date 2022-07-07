package com.gilang.network.example;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.context.ServerContext;
import com.gilang.network.netty.ws.NettyWebsocketServerRunner;
import com.gilang.network.starter.ContextLoader;

/**
 * @author gylang
 * data 2022/6/28
 */
public class ExampleServerStarter {


    public static void main(String[] args) throws Exception {

        ServerContext serverContext = new ContextLoader().contextLoad();
        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();
        NettyWebsocketServerRunner websocketServerRunner = new NettyWebsocketServerRunner();
        websocketServerRunner.start(serverContext);
    }
}
