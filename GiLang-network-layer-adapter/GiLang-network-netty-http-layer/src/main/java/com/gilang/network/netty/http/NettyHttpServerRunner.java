package com.gilang.network.netty.http;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.context.ServerContext;
import com.gilang.network.http.HttpServerRunner;
import com.gilang.network.http.config.HttpConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
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
public class NettyHttpServerRunner implements HttpServerRunner {

    protected EventLoopGroup workerGroup;
    protected EventLoopGroup bossGroup;
    protected ChannelFuture channel;

    @Override
    public void close(ServerContext serverContext) {
        channel.channel().close();
    }

    /**
     * 停止netty服务
     */
    public void destroy(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        if (bossGroup != null && !bossGroup.isShuttingDown() && !bossGroup.isShutdown()) {
            try {
                bossGroup.shutdownGracefully();
            } catch (Exception ignore) {
            }
        }
        if (workerGroup != null && !workerGroup.isShuttingDown() && !workerGroup.isShutdown()) {
            try {
                workerGroup.shutdownGracefully();
            } catch (Exception ignore) {
            }
        }
    }


    @Override
    public void start(ServerContext serverContext) throws Exception {

        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();
        // 连接线程组 工作线程组
        HttpConfig websocketConfig = beanFactoryContext.getBean(HttpConfig.class);
        workerGroup = new NioEventLoopGroup(websocketConfig.getBossGroupThreadNum());
        bossGroup = new NioEventLoopGroup(websocketConfig.getWorkerGroupThreadNum());
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 三部曲启动 handler initializer bootstrap
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.valueOf(serverContext.getLogLevel())))
                .childHandler(beanFactoryContext.getPrimaryBean(NettyHttpInitializer.class));

        channel = serverBootstrap
                .bind(websocketConfig.getPort())
                .sync();
        // 监听服务启动
        channel.syncUninterruptibly().channel().newSucceededFuture()
                .addListener(f -> log.info("[http]服务启动成功, 端口:{}", websocketConfig.getPort()));
        // 监听服务关闭
        channel.channel().closeFuture().addListener(future -> {
            log.info("http服务关闭");
            destroy(bossGroup, workerGroup);
        });

    }


}
