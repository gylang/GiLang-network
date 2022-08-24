package com.gilang.network.netty.ws;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.config.WebsocketConfig;
import com.gilang.network.context.ServerContext;
import com.gilang.network.layer.access.WebSocketServerRunner;
import com.gilang.network.netty.NettyBaseServerRunner;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gylang
 * data 2022/6/30
 */
@Slf4j
public class NettyWebsocketServerRunner extends NettyBaseServerRunner implements WebSocketServerRunner {

    @Override
    public void start(ServerContext serverContext) throws Exception {

        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();
        // 连接线程组 工作线程组
        WebsocketConfig websocketConfig = beanFactoryContext.getBean(WebsocketConfig.class);
        workerGroup = new NioEventLoopGroup(websocketConfig.getBossGroupThreadNum());
        bossGroup = new NioEventLoopGroup(websocketConfig.getWorkerGroupThreadNum());
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 三部曲启动 handler initializer bootstrap
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.valueOf(serverContext.getLogLevel())))
                .childHandler(beanFactoryContext.getPrimaryBean(NettyWebSocketInitializer.class));

        channel = serverBootstrap
                .bind(websocketConfig.getPort())
                .sync();
        // 监听服务启动
        channel.syncUninterruptibly().channel().newSucceededFuture()
                .addListener(f -> log.info("[websocket]服务启动成功, 端口:{}", websocketConfig.getPort()));
        // 监听服务关闭
        channel.channel().closeFuture().addListener(future -> {
            log.info("websocket服务关闭");
            destroy(bossGroup, workerGroup);
        });

    }



}
