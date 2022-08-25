package com.gilang.network.netty.http;

import com.gilang.common.context.BeanLoadWrapper;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gylang
 * data 2022/8/25
 */
public class NettyHttpBeanLoader implements BeanLoader {


    @Override
    public List<BeanLoadWrapper<?>> scan(ServerContext serverContext) {

        List<BeanLoadWrapper<?>> beanLoadWrappers = new ArrayList<>();




        beanLoadWrappers.add(new BeanLoadWrapper<>(NettyHttpRouterInboundHandler.class, NettyHttpRouterInboundHandler::new));
        beanLoadWrappers.add(new BeanLoadWrapper<>(NettyHttpInitializer.class, NettyHttpInitializer::new));
        beanLoadWrappers.add(new BeanLoadWrapper<>(NettyHttpServerRunner.class, NettyHttpServerRunner::new));
        return beanLoadWrappers;
    }
}
