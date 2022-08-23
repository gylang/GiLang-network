package com.gilang.network.netty;

import com.gilang.common.context.BeanLoadWrapper;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;
import com.gilang.network.netty.http.NettyHttpInitializer;
import com.gilang.network.netty.http.NettyHttpRouterInboundHandler;
import com.gilang.network.netty.http.NettyHttpServerRunner;
import com.gilang.network.netty.socket.NettySocketInitializer;
import com.gilang.network.netty.socket.NettySocketServerRunner;
import com.gilang.network.netty.ws.NettyWebSocketInitializer;
import com.gilang.network.netty.ws.NettyWebsocketServerRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gylang
 * data 2022/7/6
 */
public class NettyBeanLoader implements BeanLoader {
    @Override
    public List<BeanLoadWrapper<?>> scan(ServerContext serverContext) {

        List<BeanLoadWrapper<?>> beanLoadWrappers = new ArrayList<>();


        // 初始化
        beanLoadWrappers.add(new BeanLoadWrapper<>(NettyWebSocketInitializer.class, NettyWebSocketInitializer::new));
        beanLoadWrappers.add(new BeanLoadWrapper<>(NettySocketInitializer.class, NettySocketInitializer::new));
        // netty websocket 启器
        beanLoadWrappers.add(new BeanLoadWrapper<>(NettyWebsocketServerRunner.class, NettyWebsocketServerRunner::new));
        // netty socket 启器
        beanLoadWrappers.add(new BeanLoadWrapper<>(NettySocketServerRunner.class, NettySocketServerRunner::new));
        // netty 服务分发适器
        beanLoadWrappers.add(new BeanLoadWrapper<>(NettySocketRouterInboundHandler.class, NettySocketRouterInboundHandler::new));

        beanLoadWrappers.add(new BeanLoadWrapper<>(NettyHttpRouterInboundHandler.class, NettyHttpRouterInboundHandler::new));
        beanLoadWrappers.add(new BeanLoadWrapper<>(NettyHttpInitializer.class, NettyHttpInitializer::new));
        beanLoadWrappers.add(new BeanLoadWrapper<>(NettyHttpServerRunner.class, NettyHttpServerRunner::new));
        return beanLoadWrappers;
    }
}
