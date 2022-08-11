package com.gilang.network.netty;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;
import com.gilang.network.netty.http.NettyHttpInitializer;
import com.gilang.network.netty.http.NettyHttpRouterInboundHandler;
import com.gilang.network.netty.http.NettyHttpServerRunner;
import com.gilang.network.netty.socket.NettySocketInitializer;
import com.gilang.network.netty.socket.NettySocketServerRunner;
import com.gilang.network.netty.ws.NettyWebSocketInitializer;

/**
 * @author gylang
 * data 2022/7/6
 */
public class NettyBeanLoader implements BeanLoader {
    @Override
    public void scan(ServerContext serverContext) {
        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();

        // 初始化
        beanFactoryContext.register("NettyWebSocketInitializer", new NettyWebSocketInitializer());
        beanFactoryContext.register("NettySocketInitializer", new NettySocketInitializer());
        // netty websocket 启动器
        beanFactoryContext.register("NettyWebsocketServerRunner", new NettySocketServerRunner());
        // netty socket 启动器
        beanFactoryContext.register("NettySocketServerRunner", new NettySocketServerRunner());
        // netty 服务分发适配器
        beanFactoryContext.register("NettySocketRouterInboundHandler", new NettySocketRouterInboundHandler());

        beanFactoryContext.register(NettyHttpRouterInboundHandler.class.getName(), new NettyHttpRouterInboundHandler());
        beanFactoryContext.register(NettyHttpInitializer.class.getName(),new  NettyHttpInitializer());
        beanFactoryContext.register(NettyHttpServerRunner.class.getName(),new  NettyHttpServerRunner());

    }
}
