package com.gilang.network.netty.socket;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.config.SocketConfig;
import com.gilang.network.context.ServerContext;
import com.gilang.network.layer.access.SocketServerRunner;
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
public class NettySocketServerRunner extends NettyBaseServerRunner implements SocketServerRunner {



    @Override
    public void start(ServerContext serverContext) throws Exception {

        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();
        // 连接线程组 工作线程组
        SocketConfig socketConfig = beanFactoryContext.getBean(SocketConfig.class);
        workerGroup = new NioEventLoopGroup(socketConfig.getBossGroupThreadNum());
        bossGroup = new NioEventLoopGroup(socketConfig.getWorkerGroupThreadNum());
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 三部曲启动 handler initializer bootstrap
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.valueOf(serverContext.getLogLevel())))
                .childHandler(beanFactoryContext.getPrimaryBean(NettySocketInitializer.class));

        channel = serverBootstrap
                .bind(socketConfig.getPort())
                .sync();
        // 监听服务启动
        channel.syncUninterruptibly().channel().newSucceededFuture()
                .addListener(f -> log.info("[socket]服务启动成功, 端口:{}", socketConfig.getPort()));
        // 监听服务关闭
        channel.channel().closeFuture().addListener(future -> {
            log.info("socket服务关闭");
            this.destroy(bossGroup, workerGroup);
        });
    }


}
